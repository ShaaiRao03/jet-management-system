package com.example.programming2_c1.Elements;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Payment {

    public static int getPaymentID(String paymentType){

        int paymentID = -1;
        Connection connectPayment= null;
        ResultSet resultPayment = null;
        PreparedStatement statementPayment = null;

        try {
            String obtainPaymentID = "SELECT methodID FROM paymentmethod WHERE methodName = ?;";
            connectPayment = JetSysDatabase.getConnection();

            statementPayment = connectPayment.prepareStatement(obtainPaymentID);
            statementPayment.setString(1,paymentType);
            resultPayment = statementPayment.executeQuery();

            while(resultPayment.next()){
                paymentID = resultPayment.getInt(1);
            }


        }catch (Exception e){
            System.out.println("Payment (getPaymentID()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectPayment,statementPayment,resultPayment);
        }
        return paymentID;
    }

    public static String getPaymentName(int paymentID){

        String paymentName = "";
        Connection connectPayment= null;
        ResultSet resultPayment = null;
        PreparedStatement statementPayment = null;

        try {
            String obtainPaymentName = "SELECT methodName FROM paymentmethod WHERE methodID = ?;";
            connectPayment = JetSysDatabase.getConnection();

            statementPayment = connectPayment.prepareStatement(obtainPaymentName);
            statementPayment.setInt(1,paymentID);
            resultPayment = statementPayment.executeQuery();

            while(resultPayment.next()){
                paymentName = resultPayment.getString(1);
            }


        }catch (Exception e){
            System.out.println("Payment (getPaymentName()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectPayment,statementPayment,resultPayment);
        }
        return paymentName;
    }

}
