package com.example.programming2_c1.JetClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.*;

public class JetExterior {

    private String jetID;
    private int exteriorID;
    private String stripe;
    private String basePaint;

    public String getJetID() {
        return jetID;
    }

    public void setJetID(String jetID) {
        this.jetID = jetID;
    }

    public int getExteriorID() {
        return exteriorID;
    }

    public void setExteriorID(int exteriorID) {
        this.exteriorID = exteriorID;
    }

    public String getStripe() {
        return stripe;
    }

    public void setStripe(String stripe) {
        this.stripe = stripe;
    }

    public String getBasePaint() {
        return basePaint;
    }

    public void setBasePaint(String basePaint) {
        this.basePaint = basePaint;
    }

    public JetExterior(){};

    public JetExterior(String jetID, String stripe, String basePaint) {
        this.jetID = jetID;
        this.stripe = stripe;
        this.basePaint = basePaint;
    }


    public static void addExteriorToDatabase(String jetID, String stripe , String basePaint){

        Connection connectExterior = null;
        ResultSet resultExterior = null;
        PreparedStatement statementExterior = null;

        try {
            String exteriorInsert_query = "INSERT INTO `exterior`(`jetID`, `stripe`, `basepaint`) VALUES (?,?,?)";
            connectExterior = JetSysDatabase.getConnection();

            statementExterior = connectExterior.prepareStatement(exteriorInsert_query);
            statementExterior.setString(1,jetID);
            statementExterior.setString(2,stripe);
            statementExterior.setString(3,basePaint);

            statementExterior.execute();

        } catch (SQLException e) {
            System.out.println("Exterior (addExteriorToDatabase()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connectExterior,statementExterior,resultExterior);
        }
    }

    public static void updateExterior(String jetID, String stripe , String basePaint){

        Connection connectExterior = null;
        ResultSet resultExterior = null;
        PreparedStatement statementExterior = null;

        try {
            String updateExterior_query = "UPDATE `exterior` SET `jetID`=?,`stripe`=?,`basepaint`=? WHERE `jetID`=?";
            connectExterior = JetSysDatabase.getConnection();

            statementExterior = connectExterior.prepareStatement(updateExterior_query);
            statementExterior.setString(1,jetID);
            statementExterior.setString(2,stripe);
            statementExterior.setString(3,basePaint);
            statementExterior.setString(4,jetID);

            statementExterior.execute();

        } catch (SQLException e) {
            System.out.println("Exterior (updateExterior()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connectExterior,statementExterior,resultExterior);
        }
    }

}
