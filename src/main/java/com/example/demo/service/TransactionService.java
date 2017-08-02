package com.example.demo.service;

import com.example.demo.model.StatisticModel;
import org.springframework.stereotype.Service;

/**
 * Created by colin on 02.08.17.
 */
@Service
public interface TransactionService {
    StatisticModel calcStatistics();
    void clearTransactions();
}
