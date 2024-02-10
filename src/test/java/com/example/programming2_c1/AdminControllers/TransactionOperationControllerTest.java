package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.JetClasses.PrivateJet;
import com.example.programming2_c1.UserClasses.Transaction;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.dsig.TransformService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionOperationControllerTest {

    @Test
    void redisplayBasedOnTransactionFilter() {

        //priceChanged will always be checked (first priority)
        //prefSearch = empty , prefMonth = empty , prefYear = empty
        //search can be done by using their respective transaction ID
        //the month name must be in uppercase letter

        //testing with Approved transactions
        Transaction transaction1 = Transaction.getTransactionDetailsGivenID(2); //date : 2023-04-26 , amount : 3000000
        Transaction transaction2 = Transaction.getTransactionDetailsGivenID(11); //date : 2023-05-04 , amount : 50000000
        Transaction transaction3 = Transaction.getTransactionDetailsGivenID(13); //date : 2023-05-09 , amount : 2000000

        ArrayList<Transaction> transactionList =  new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);

        //creating test object
        TransactionOperationController transactionOperationController = new TransactionOperationController();

        // test scenario 1: no filter [return all]
        ArrayList<Transaction> result1 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"",null,null, 0L);
        assertEquals(transactionList, result1);

        //test scenario 2: prefSearch only  [correct ID]
        ArrayList<Transaction> result2 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"2",null,null, 0L);
        ArrayList<Transaction> expected2 = new ArrayList<>();
        expected2.add(transaction1);
        assertEquals(expected2, result2);

        //test scenario 3: prefSearch only  [wrong ID]
        ArrayList<Transaction> result3 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"4",null,null, 0L);
        ArrayList<Transaction> expected3 = new ArrayList<>();
        assertEquals(expected3, result3);

        //test scenario 4: month only
        ArrayList<Transaction> result4 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"","MAY",null, 0L);
        ArrayList<Transaction> expected4 = new ArrayList<>();
        expected4.add(transaction2);
        expected4.add(transaction3);
        assertEquals(expected4, result4);

        //test scenario 5: year only
        ArrayList<Transaction> result5 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"",null,"2023", 0L);
        assertEquals(transactionList, result5);

        //test scenario 6: prefSearch and month
        ArrayList<Transaction> result6 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"11","MAY",null, 0L);
        ArrayList<Transaction> expected6 = new ArrayList<>();
        expected6.add(transaction2);
        assertEquals(expected6, result6);

        //test scenario 7: prefSearch and year
        ArrayList<Transaction> result7 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"11",null,"2023", 0L);
        ArrayList<Transaction> expected7 = new ArrayList<>();
        expected7.add(transaction2);
        assertEquals(expected7, result7);

        //test scenario 8: month and year
        ArrayList<Transaction> result8 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"","APRIL","2023", 0L);
        ArrayList<Transaction> expected8 = new ArrayList<>();
        expected8.add(transaction1);
        assertEquals(expected8, result8);

        //test scenario 9: prefSearch,month and year
        ArrayList<Transaction> result9 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"11","MAY","2023", 0L);
        ArrayList<Transaction> expected9 = new ArrayList<>();
        expected9.add(transaction2);
        assertEquals(expected9, result9);

        //test scenario 10: price range only
        ArrayList<Transaction> result10 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"",null,null, 10L);
        ArrayList<Transaction> expected10 = new ArrayList<>();
        expected10.add(transaction2);
        assertEquals(expected10, result10);

        //test scenario 11: combination of all [but the condition doesnt satisfy the set]
        ArrayList<Transaction> result11 = transactionOperationController.redisplayBasedOnTransactionFilter(transactionList,"13","MAY","2023", 10L);
        ArrayList<Transaction> expected11 = new ArrayList<>();
        assertEquals(expected11, result11);

    }
}