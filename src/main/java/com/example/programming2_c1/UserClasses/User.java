package com.example.programming2_c1.UserClasses;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetClasses.JetData;
import com.example.programming2_c1.JetSysDatabase;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {

    String userName;
    String identity_card;
    String first_name;
    String last_name;
    String email;
    String phoneNum;
    String address;
    String country;
    String role;
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User(String userName, String identity_card, String first_name, String last_name, String email, String phoneNum,
                String address, String country, String role, String password) {
        this.userName = userName;
        this.identity_card = identity_card;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.country = country;
        this.role = role;
        this.password = password;
    }

    public User(){}


    public static void updateUserJet(String userName, String first_name, String last_name, String email, String phoneNum,
    String address, String prevUsername) throws InterruptedException {

        Thread updateUser = new Thread(() ->{

            Connection connectUser = null;
            ResultSet resultUser = null;
            PreparedStatement statementUser = null;

            try {
                String updateUser_query = "UPDATE userjet SET userName= ? ,first_name= ?,last_name= ?,email= ?,phoneNum= ?,address= ?  WHERE userName = ?; ";
                connectUser = JetSysDatabase.getConnection();

                statementUser = connectUser.prepareStatement(updateUser_query);
                statementUser.setString(1,userName);
                statementUser.setString(2,first_name);
                statementUser.setString(3,last_name);
                statementUser.setString(4,email);
                statementUser.setString(5,phoneNum);
                statementUser.setString(6,address);
                statementUser.setString(7,prevUsername);

                statementUser.execute();

            }catch (Exception e){
                System.out.println("User (updateUserJet()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectUser,statementUser,resultUser);
            }
        }); updateUser.start(); updateUser.join();

    }


    public static void insertToUser(String userName, String identity_card, String first_name, String last_name, String email, String phoneNum,
                                    String address, String country) throws InterruptedException {

        Thread insertUser = new Thread(() ->{

            Connection connectUser = null;
            ResultSet resultUser = null;
            PreparedStatement statementUser = null;

            try {
                String insertUser_query = " INSERT INTO userjet VALUES ( ? , ? , ? , ? , ? , ? , ? , ? ) ";
                connectUser = JetSysDatabase.getConnection();

                statementUser = connectUser.prepareStatement(insertUser_query);
                statementUser.setString(1,userName);
                statementUser.setString(2,identity_card);
                statementUser.setString(3,first_name);
                statementUser.setString(4,last_name);
                statementUser.setString(5,email);
                statementUser.setString(6,phoneNum);
                statementUser.setString(7,address);
                statementUser.setString(8,country);

                statementUser.execute();

            }catch (Exception e){
                System.out.println("User (insertToUser()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectUser,statementUser,resultUser);
            }
        }); insertUser.start(); insertUser.join();

    }

}
