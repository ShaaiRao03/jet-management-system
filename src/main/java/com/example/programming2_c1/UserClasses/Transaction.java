package com.example.programming2_c1.UserClasses;

import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetClasses.PrivateJet;
import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Transaction {

    int transactionID;
    int jetQuantity;
    String additionalFeatures;
    long totalAmount;
    int paymentmethod;
    String bankName;
    String deliveryCountry;
    String deliveryLocation;
    String jetID;
    LocalDate transactionDate;
    String userName;

    String jetName; //try without this for table

    public String getJetName() {
        return jetName;
    }

    public void setJetName(PrivateJet jet) {
        this.jetName = jet.getJetName();
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getJetQuantity() {
        return jetQuantity;
    }

    public void setJetQuantity(int jetQuantity) {
        this.jetQuantity = jetQuantity;
    }

    public String getAdditionalFeatures() {
        return additionalFeatures;
    }

    public void setAdditionalFeatures(String additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(int paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getJetID() {
        return jetID;
    }

    public void setJetID(String jetID) {
        this.jetID = jetID;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Transaction(int transactionID, int jetQuantity, String additionalFeatures, long totalAmount, int paymentmethod, String bankName, String deliveryCountry, String deliveryLocation, String jetID, LocalDate transactionDate, String userName) throws InterruptedException {
        this.transactionID = transactionID;
        this.jetQuantity = jetQuantity;
        this.additionalFeatures = additionalFeatures;
        this.totalAmount = totalAmount;
        this.paymentmethod = paymentmethod;
        this.bankName = bankName;
        this.deliveryCountry = deliveryCountry;
        this.deliveryLocation = deliveryLocation;
        this.jetID = jetID;
        this.transactionDate = transactionDate;
        this.userName = userName;
        setJetName(Objects.requireNonNull(JetData.getPrivateJetGivenID(jetID)));
    }


    public Transaction(){}

    public static void addTransactionToDatabase(int cartID){
        //add transaction to database once approved by admin

        Thread addTransaction = new Thread(() ->{

            //to execute SELECT QUERY
            Connection connectTransaction = null;
            ResultSet resultTransaction = null;
            PreparedStatement statementTransaction = null;

            //to execute INSERT QUERY
            Connection connectTransactionInsert = null;
            ResultSet resultTransactionInsert = null;
            PreparedStatement statementTransactionInsert = null;

            //to get the current date
            LocalDate date = LocalDate.now();

            try {

                String obtainAllDetails = "SELECT * FROM cart JOIN submission USING(cartID) WHERE cartID = ?";
                connectTransaction = JetSysDatabase.getConnection();

                statementTransaction = connectTransaction.prepareStatement(obtainAllDetails);
                statementTransaction.setInt(1,cartID);
                resultTransaction = statementTransaction.executeQuery();

                while (resultTransaction.next()){

                    String transactionInsert_query = "INSERT INTO transaction (jetQuantity,additionalFeatures,totalAmount,paymentmethod,bankName,deliveryCountry,deliveryLocation,jetID,transactionDate,userName) VALUES (?,?,?,?,?,?,?,?,?,?)";
                    connectTransactionInsert = JetSysDatabase.getConnection();
                    statementTransactionInsert = connectTransactionInsert.prepareStatement(transactionInsert_query);

                    statementTransactionInsert.setInt(1,resultTransaction.getInt("jetQuantity"));
                    statementTransactionInsert.setString(2,resultTransaction.getString("additionalFeatures"));
                    statementTransactionInsert.setLong(3,resultTransaction.getLong("totalAmount"));
                    statementTransactionInsert.setString(4,resultTransaction.getString("paymentmethod"));
                    statementTransactionInsert.setString(5,resultTransaction.getString("bankName"));
                    statementTransactionInsert.setString(6,resultTransaction.getString("deliveryCountry"));
                    statementTransactionInsert.setString(7,resultTransaction.getString("deliveryLocation"));
                    statementTransactionInsert.setString(8,resultTransaction.getString("jetID"));
                    statementTransactionInsert.setDate(9, Date.valueOf(date));
                    statementTransactionInsert.setString(10,resultTransaction.getString("userName"));

                    statementTransactionInsert.execute();
                }

            }catch (Exception e){
                System.out.println("TransactionController (addTransactionToDatabase()) has not been connected to database");
                e.printStackTrace();

            } finally {
                JetSysDatabase.closeConnection(connectTransaction,statementTransaction,resultTransaction);
                JetSysDatabase.closeConnection(connectTransactionInsert,statementTransactionInsert,resultTransactionInsert);
            }
        }); addTransaction.start();
    }


    public static ArrayList<Transaction> getAllTransactionDetails(String userName) throws InterruptedException {

        ArrayList<Transaction> transactions = new ArrayList<>();

            Connection connectTransaction = null;
            ResultSet resultTransaction = null;
            PreparedStatement statementTransaction = null;

            try {

                if (userName.equals("")){

                    String obtainTransaction = "SELECT * FROM transaction";
                    connectTransaction = JetSysDatabase.getConnection();

                    statementTransaction = connectTransaction.prepareStatement(obtainTransaction);

                } else {

                    String obtainTransaction = "SELECT * FROM transaction WHERE userName = ?";
                    connectTransaction = JetSysDatabase.getConnection();

                    statementTransaction = connectTransaction.prepareStatement(obtainTransaction);
                    statementTransaction.setString(1, userName);
                }

                resultTransaction = statementTransaction.executeQuery();


                while (resultTransaction.next()){
                    LocalDate tempDate = resultTransaction.getDate("transactionDate").toLocalDate();
                    transactions.add(new Transaction(resultTransaction.getInt("transactionID"),resultTransaction.getInt("jetQuantity"),resultTransaction.getString("additionalFeatures"),
                            resultTransaction.getLong("totalAmount"),resultTransaction.getInt("paymentmethod"),resultTransaction.getString("bankName"),resultTransaction.getString("deliveryCountry"),resultTransaction.getString("deliveryLocation"),
                            resultTransaction.getString("jetID"),tempDate,resultTransaction.getString("userName")));
                }


            }catch (Exception e){
                System.out.println("TransactionController (getAllTransactionDetails()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectTransaction,statementTransaction,resultTransaction);
            }

        return transactions;
    }

    public static Transaction getTransactionDetailsGivenID(int transactionID){

        Transaction transaction = new Transaction();

        Connection connectTransaction = null;
        ResultSet resultTransaction = null;
        PreparedStatement statementTransaction = null;

        try {

            String obtainTransaction = "SELECT * FROM transaction WHERE transactionID = ?";
            connectTransaction = JetSysDatabase.getConnection();

            statementTransaction = connectTransaction.prepareStatement(obtainTransaction);
            statementTransaction.setInt(1, transactionID);


            resultTransaction = statementTransaction.executeQuery();


            while (resultTransaction.next()){
                LocalDate tempDate = resultTransaction.getDate("transactionDate").toLocalDate();
                transaction = new Transaction(resultTransaction.getInt("transactionID"),resultTransaction.getInt("jetQuantity"),resultTransaction.getString("additionalFeatures"),
                        resultTransaction.getLong("totalAmount"),resultTransaction.getInt("paymentmethod"),resultTransaction.getString("bankName"),resultTransaction.getString("deliveryCountry"),resultTransaction.getString("deliveryLocation"),
                        resultTransaction.getString("jetID"),tempDate,resultTransaction.getString("userName"));
            }


        }catch (Exception e){
            System.out.println("TransactionController (getTransactionDetailsGivenID()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectTransaction,statementTransaction,resultTransaction);
        }

        return transaction;

    }

    @Override
    public String toString() {
        return ""+transactionID;
    }
}
