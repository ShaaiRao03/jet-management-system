package com.example.programming2_c1.JetClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JetAirframe {

    private String jetID;
    private int airFrameID;
    private int timeSinceNew;
    private int landings;

    public String getJetID() {
        return jetID;
    }

    public void setJetID(String jetID) {
        this.jetID = jetID;
    }

    public int getAirFrameID() {
        return airFrameID;
    }

    public void setAirFrameID(int airFrameID) {
        this.airFrameID = airFrameID;
    }

    public int getTimeSinceNew() {
        return timeSinceNew;
    }

    public void setTimeSinceNew(int timeSinceNew) {
        this.timeSinceNew = timeSinceNew;
    }

    public int getLandings() {
        return landings;
    }

    public void setLandings(int landings) {
        this.landings = landings;
    }

    public JetAirframe(){}

    public JetAirframe(String jetID,  int timeSinceNew, int landings) {
        this.jetID = jetID;
        this.timeSinceNew = timeSinceNew;
        this.landings = landings;
    }

    public static void addAirFrameToDatabase(String jetID, int timeSinceNew , int landings){

        Connection connnectAirFrame = null;
        ResultSet resultAirFrame = null;
        PreparedStatement statementAirFrame = null;

        try {
            String airFrameInsert_query = "INSERT INTO `airframe` VALUES (?,?,?)";
            connnectAirFrame = JetSysDatabase.getConnection();

            statementAirFrame = connnectAirFrame.prepareStatement(airFrameInsert_query);
            statementAirFrame.setString(1,jetID);
            statementAirFrame.setInt(2,timeSinceNew);
            statementAirFrame.setInt(3,landings);

            statementAirFrame.execute();

        } catch (SQLException e) {
            System.out.println("AirFrame (addAirFrameToDatabase()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connnectAirFrame,statementAirFrame,resultAirFrame);
        }
    }

    public static void updateAirFrame(String jetID, int timeSinceNew , int landings){

        Connection connnectAirFrame = null;
        ResultSet resultAirFrame = null;
        PreparedStatement statementAirFrame = null;

        try {
            String updateAirFrame_query = "UPDATE `airframe` SET `jetID`= ?,`timeSinceNew`= ?,`landings`= ? WHERE `jetID`= ?";
            connnectAirFrame = JetSysDatabase.getConnection();

            statementAirFrame = connnectAirFrame.prepareStatement(updateAirFrame_query);
            statementAirFrame.setString(1,jetID);
            statementAirFrame.setInt(2,timeSinceNew);
            statementAirFrame.setInt(3,landings);
            statementAirFrame.setString(4,jetID);

            statementAirFrame.execute();

        } catch (SQLException e) {
            System.out.println("Private (updateAirFrame()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connnectAirFrame,statementAirFrame,resultAirFrame);
        }
    }

}
