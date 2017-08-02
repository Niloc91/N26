package com.example.demo.service.impl;


import com.example.demo.model.StatisticModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.service.CacheStorage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by colin on 02.08.17.
 */
//@Service
public class CacheStorageImpl implements CacheStorage{

    private long count;
    private double max;
    private double avg;
    private double min;
    private double sum;
    Instant instant;
    long timeNow;


    ConcurrentHashMap<Long,Double> data;

    public CacheStorageImpl(){
        data = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void addTransaction(TransactionModel transactionModel) {
        this.data.put(transactionModel.getTimestamp(),transactionModel.getAmount());
        this.instant = Instant.now();
        this.timeNow = instant.toEpochMilli();
        recalculate(timeNow);
    }

    @Override
    public void clearData() {
        data = new ConcurrentHashMap<>();
    }

    private void recalculate(long currentTime){
        double value;

        this.max = 100;
        this.min = 100000;
        this.avg = 0;
        this.sum = 0;
        this.count = 0;

        for(HashMap.Entry<Long,Double> entry: data.entrySet()){
            if(entry.getKey()< currentTime + 60000){
                value = entry.getValue();

                if(this.max<value){
                    this.max = value;
                }

                if(this.min > value){
                    this.min = value;
                }

                this.sum = this.sum + value;
                this.count++;
            }
        }

        if(!(this.count==0)){
            this.avg = this.sum/this.count;
        }else{
            this.avg = 0;
        }
    }



    @Override
    public StatisticModel getStatModel() {
        return new StatisticModel(this.sum,this.avg,this.max,this.min,this.count);
    }
}
