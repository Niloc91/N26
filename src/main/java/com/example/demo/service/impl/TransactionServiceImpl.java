package com.example.demo.service.impl;

import com.example.demo.model.StatisticModel;
import com.example.demo.service.CacheStorage;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by colin on 02.08.17.
 */
@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    CacheStorage cacheStorage;

    private double sum;
    private double max;
    private double min;
    private double avg;
    private long count;


    @Override
    public StatisticModel calcStatistics() {
        return cacheStorage.getStatModel();//new StatisticModel(this.sum,this.avg,this.max,this.min,this.count);
    }

    @Override
    public void clearTransactions() {
        cacheStorage.clearData();
    }


}
