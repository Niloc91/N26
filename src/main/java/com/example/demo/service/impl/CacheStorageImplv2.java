package com.example.demo.service.impl;

import com.example.demo.model.StatisticModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.service.CacheStorage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by colin on 02.08.17.
 */
@Service
public class CacheStorageImplv2 implements CacheStorage{
    private long count;
    private double max;
    private double avg;
    private double min;
    private double sum;
    Instant instant;
    long timeNow;


    ConcurrentHashMap<Long,Double> data;

    public CacheStorageImplv2(){
        data = new ConcurrentHashMap<>();
        this.count = 0;
        this.max = 0;
        this.avg = 0;
        this.min = 10000000;
        this.sum = 0;
        this.instant = Instant.now();
        timeNow = this.instant.toEpochMilli();

    }

    @Override
    public synchronized void addTransaction(TransactionModel transactionModel) {
        this.data.put(transactionModel.getTimestamp(),transactionModel.getAmount());
    }

    @Override
    public void clearData() {
        data = new ConcurrentHashMap<>();
    }


    public ConcurrentHashMap<Long, Double> getData() {
        return data;
    }

    public void setData(ConcurrentHashMap<Long, Double> data) {
        this.data = data;
    }

    private synchronized void calculateStats(){
        long currentTime = Instant.now().toEpochMilli();

        //streams
        List<Double> filteredList = data
                .entrySet()
                .stream()
                .filter(p -> p.getKey().longValue()>currentTime-60000) //Filter out old values older than 60 seconds
                .map(p->p.getValue()).collect(Collectors.toList());


        DoubleSummaryStatistics stats = filteredList.stream().collect(Collectors.summarizingDouble(Double::doubleValue));

        this.avg = stats.getAverage();
        this.count = stats.getCount();
        this.max = stats.getMax();
        this.sum = stats.getSum();
        this.min = stats.getMin();

    }

    @Override
    public StatisticModel getStatModel() {

        calculateStats();

        return new StatisticModel(this.sum,this.avg,this.max,this.min,this.count);
    }
}
