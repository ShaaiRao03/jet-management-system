package com.example.programming2_c1.UserClasses;

import org.junit.jupiter.api.Test;

import javax.xml.crypto.dsig.TransformService;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void getAllTransactionDetails() throws InterruptedException {

        // a = username of admin , this test is to make sure that admin does not have any transaction details
        ArrayList<Transaction> adminTransaction = new ArrayList<>();
        assertEquals(adminTransaction,Transaction.getAllTransactionDetails("a"));

    }
}