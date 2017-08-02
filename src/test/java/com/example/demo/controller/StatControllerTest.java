package com.example.demo.controller;

import com.example.demo.model.StatisticModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.service.TransactionService;
import net.minidev.json.JSONValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.servlet.ServletContext;

import java.time.Instant;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by colin on 02.08.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    StatController statController;




    @Test
    public void contexLoads() throws Exception {
        assertNotNull(statController);
    }


    @Test
    public void addTransaction() throws Exception {
        Instant instant = Instant.now();
        long timeNow = instant.toEpochMilli();

        TransactionModel transactionModel = new TransactionModel(100,timeNow);
        TransactionModel transactionModelold = new TransactionModel(100,timeNow-70000);

        this.mockMvc.perform(post("/api/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(JSONValue.toJSONString(transactionModel))
        ).andExpect(status().is2xxSuccessful());

        this.mockMvc.perform(post("/api/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(JSONValue.toJSONString(transactionModelold))
        ).andExpect(status().is(204));

        transactionModel = new TransactionModel(10,timeNow+1);
        this.mockMvc.perform(post("/api/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(JSONValue.toJSONString(transactionModel))
        ).andExpect(status().is(201));

    }

    @Test
    public void stats() throws Exception {
        this.mockMvc.perform(get("/api/statistics")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("")));
    }

}