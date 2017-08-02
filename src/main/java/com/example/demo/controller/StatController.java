package com.example.demo.controller;

import com.example.demo.model.StatisticModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.service.CacheStorage;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;


/**
 * Created by colin on 02.08.17.
 */
@RestController
@RequestMapping(value = "/api")
public class StatController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CacheStorage cacheStorage;

    @RequestMapping(value = "/transaction", method = RequestMethod.POST,consumes = "application/json")
    public synchronized ResponseEntity<Void> addTransaction(@RequestBody TransactionModel transactionModel){
        Instant instant = Instant.now();
        long timeNow = instant.toEpochMilli();

        if(transactionModel.getTimestamp()> (timeNow-60000)){
            cacheStorage.addTransaction(transactionModel);
            return ResponseEntity.status(201).build();
        }else{
            return ResponseEntity.status(204).build();
        }
    }


    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public StatisticModel stats(){
        return transactionService.calcStatistics();
    }


}
