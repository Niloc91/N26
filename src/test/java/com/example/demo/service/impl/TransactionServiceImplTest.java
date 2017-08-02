package com.example.demo.service.impl;

import com.example.demo.model.StatisticModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.service.TransactionService;
import net.minidev.json.JSONValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by colin on 02.08.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TransactionService transactionService;

    private void addTestTransaction(TransactionModel transactionModel) throws Exception{
        this.mockMvc.perform(post("/api/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(JSONValue.toJSONString(transactionModel))
        ).andExpect(status().is(201));
    }


    @Before
    public void setUp() throws Exception {
        Instant instant = Instant.now();
        long timeNow = instant.toEpochMilli();

        addTestTransaction(new TransactionModel(50,timeNow)); //Min
        for(int i=0;i<8;i++){
            addTestTransaction(new TransactionModel(100,timeNow+1+i));
        }

        addTestTransaction(new TransactionModel(150,timeNow+20)); //Max
    }

    @After
    public void tearDown() throws Exception {
        transactionService.clearTransactions();
    }

    @Test
    public void calcStatistics() throws Exception {
        StatisticModel stats = transactionService.calcStatistics();

        assertEquals(150,stats.getMax(),0);
        assertEquals(50,stats.getMin(),0);
        assertEquals(100,stats.getAvg(),0);
        assertEquals(10,stats.getCount(),0);
        assertEquals(1000,stats.getSum(),0);

    }

}