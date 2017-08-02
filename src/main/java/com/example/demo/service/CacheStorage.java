package com.example.demo.service;

import com.example.demo.model.StatisticModel;
import com.example.demo.model.TransactionModel;
import org.springframework.stereotype.Service;

/**
 * Created by colin on 02.08.17.
 */
@Service
public interface CacheStorage {
    void addTransaction(TransactionModel transactionModel);
    void clearData();
    StatisticModel getStatModel();
}
