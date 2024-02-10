package com.example.programming2_c1.JetClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JetInterior {

    private String jetID;
    private int interiorID;
    private String forwardCabinConfig;
    private String aftCabinConfig;
    private int numPassengers;

    public String getJetID() {
        return jetID;
    }

    public void setJetID(String jetID) {
        this.jetID = jetID;
    }

    public int getInteriorID() {
        return interiorID;
    }

    public void setInteriorID(int interiorID) {
        this.interiorID = interiorID;
    }

    public String getForwardCabinConfig() {
        return forwardCabinConfig;
    }

    public void setForwardCabinConfig(String forwardCabinConfig) {
        this.forwardCabinConfig = forwardCabinConfig;
    }

    public String getAftCabinConfig() {
        return aftCabinConfig;
    }

    public void setAftCabinConfig(String aftCabinConfig) {
        this.aftCabinConfig = aftCabinConfig;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }

    public JetInterior(){}

    public JetInterior(String jetID, String forwardCabinConfig, String aftCabinConfig, int numPassengers) {
        this.jetID = jetID;
        this.forwardCabinConfig = forwardCabinConfig;
        this.aftCabinConfig = aftCabinConfig;
        this.numPassengers = numPassengers;
    }


    public static void addInteriorToDatabase(String jetID, String  forwardCabinConfig , String aftCabinConfig, int numPassengers){

        Connection connnectInterior = null;
        ResultSet resultInterior = null;
        PreparedStatement statementInterior = null;

        try {
            String interiorInsert_query = "INSERT INTO `interior`(`jetID`, `forwardCabinConfig`, `AftCabinConfig`, `numPassengers`) VALUES (?,?,?,?)";
            connnectInterior = JetSysDatabase.getConnection();

            statementInterior = connnectInterior.prepareStatement(interiorInsert_query);
            statementInterior.setString(1,jetID);
            statementInterior.setString(2,forwardCabinConfig);
            statementInterior.setString(3,aftCabinConfig);
            statementInterior.setInt(4,numPassengers);

            statementInterior.execute();

        } catch (SQLException e) {
            System.out.println("Interior (addInteriorToDatabase()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connnectInterior,statementInterior,resultInterior);
        }
    }

    public static void updateInterior(String jetID, String forwardCabinConfig , String aftCabinConfig, int numPassengers){

        Connection connnectInterior = null;
        ResultSet resultInterior = null;
        PreparedStatement statementInterior = null;

        try {
            String updateInterior_query = "UPDATE `interior` SET `jetID`=?,`forwardCabinConfig`=?,`AftCabinConfig`=?,`numPassengers`=? WHERE `jetID`=?";
            connnectInterior = JetSysDatabase.getConnection();

            statementInterior = connnectInterior.prepareStatement(updateInterior_query);
            statementInterior.setString(1,jetID);
            statementInterior.setString(2,forwardCabinConfig);
            statementInterior.setString(3,aftCabinConfig);
            statementInterior.setInt(4,numPassengers);
            statementInterior.setString(5,jetID);

            statementInterior.execute();

        } catch (SQLException e) {
            System.out.println("Interior (updateInterior()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connnectInterior,statementInterior,resultInterior);
        }
    }

}
