package com.example.programming2_c1.UserClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.lang.ref.Cleaner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Client extends User{

    String job;
    String company;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Client(String userName, String identity_card, String first_name, String last_name, String email, String job, String company, String phoneNum,
                  String address, String country, String role, String password){

        super(userName, identity_card,first_name, last_name, email, phoneNum, address,country, role, password);
        this.company = company;
        this.job = job;

    }

    public Client(){}


    public static Client getClientGivenUsername(String username) {

        Client client = new Client();
        Connection connectClient = null;
        ResultSet resultClient = null;
        PreparedStatement statementClient = null;

        try {

            String obtainClient = "SELECT * FROM client JOIN userjet USING(userName) JOIN logindetailsjet USING(userName) WHERE userName = ? ;";
            connectClient = JetSysDatabase.getConnection();

            statementClient = connectClient.prepareStatement(obtainClient);
            statementClient.setString(1,username);
            resultClient = statementClient.executeQuery();

            while (resultClient.next()){
                client =  new Client(resultClient.getString("userName"),resultClient.getString("identity_card"),resultClient.getString("first_name"),
                        resultClient.getString("last_name"),resultClient.getString("email"),resultClient.getString("job"),resultClient.getString("company"),
                        resultClient.getString("phoneNum"),resultClient.getString("address"),resultClient.getString("country"),resultClient.getString("role"),resultClient.getString("password"));
            }

        }catch (Exception e){
            System.out.println("JetData (generateModelList()) has not been connected to database");

        }finally {
            JetSysDatabase.closeConnection(connectClient,statementClient,resultClient);
        }

        return client;

    }

    public static void updateClient(String job, String company, String prevUsername) throws InterruptedException {

        Thread updateClient = new Thread(() ->{
            Connection connectClient = null;
            ResultSet resultClient = null;
            PreparedStatement statementClient = null;

            try {
                String updateClient_query = "UPDATE client SET job= ? , company = ? WHERE userName = ?; ";
                connectClient = JetSysDatabase.getConnection();

                statementClient = connectClient.prepareStatement(updateClient_query);
                statementClient.setString(1,job);
                statementClient.setString(2,company);
                statementClient.setString(3,prevUsername);

                statementClient.execute();

            }catch (Exception e){
                System.out.println("Client (updateClient()) has not been connected to database");


            } finally {
                JetSysDatabase.closeConnection(connectClient,statementClient,resultClient);
            }
        }); updateClient.start(); updateClient.join();

    }


    public static void insertToClient(String username , String job, String company) throws InterruptedException {

        Thread insertClient = new Thread(() ->{

            Connection connectClient = null;
            ResultSet resultClient = null;
            PreparedStatement statementClient = null;

            try {
                String insertUser_query = " INSERT INTO client VALUES ( ? , ? , ? ) ";
                connectClient = JetSysDatabase.getConnection();

                statementClient = connectClient.prepareStatement(insertUser_query);
                statementClient.setString(1,username);
                statementClient.setString(2,job);
                statementClient.setString(3,company);

                statementClient.execute();

            }catch (Exception e){
                System.out.println("Client (insertClient()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectClient,statementClient,resultClient);
            }
        }); insertClient.start(); insertClient.join();

    }
}
