package com.example.programming2_c1.JetClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.*;

public class Manufacturer {

    private int manufacturerID;
    private String manufacturerName;

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Manufacturer(){}
    public Manufacturer(int manufacturerID, String manufacturerName) {
        this.manufacturerID = manufacturerID;
        this.manufacturerName = manufacturerName;
    }


    public static void updateManufacturer(int manufacturerID, String manufacturerName){

            Connection connectManufacturer = null;
            ResultSet resultManufacturer = null;
            PreparedStatement statementManufacturer = null;

            try {
                String updateManufacturer_query = "UPDATE manufacturer SET manufacturerID = ? , manufacturerName = ? WHERE manufacturerID = ?; ";
                connectManufacturer = JetSysDatabase.getConnection();

                statementManufacturer = connectManufacturer.prepareStatement(updateManufacturer_query);
                statementManufacturer.setInt(1,manufacturerID);
                statementManufacturer.setString(2,manufacturerName);
                statementManufacturer.setInt(3,manufacturerID);

                statementManufacturer.execute();

            }catch (Exception e){
                System.out.println("Manufacturer (updateManufacturer()) has not been connected to database");
                e.printStackTrace();

            } finally {
                JetSysDatabase.closeConnection(connectManufacturer,statementManufacturer,resultManufacturer);
            }
    }


    public static void removeManufacturerFromDatabase(int manufacturerID){

            Connection connectManufacturer = null;
            ResultSet resultManufacturer = null;
            PreparedStatement statementManufacturer = null;

            try {
                String manufacturerDelete_query = "DELETE FROM manufacturer WHERE manufacturerID = ?";
                connectManufacturer = JetSysDatabase.getConnection();

                statementManufacturer = connectManufacturer.prepareStatement(manufacturerDelete_query);
                statementManufacturer.setInt(1,manufacturerID);

                statementManufacturer.execute();

            }catch (Exception e){
                System.out.println("Manufacturer (removeManufacturerFromDatabase()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectManufacturer,statementManufacturer,resultManufacturer);
            }
    }


    public static void addManufacturerToDatabase(int manufacturerID, String manufacturerName){

            Connection connectManufacturer = null;
            ResultSet resultManufacturer = null;
            PreparedStatement statementManufacturer = null;

            try {
                String manufacturerInsert_query = "INSERT INTO manufacturer VALUES (?,?)";
                connectManufacturer = JetSysDatabase.getConnection();

                statementManufacturer = connectManufacturer.prepareStatement(manufacturerInsert_query);
                statementManufacturer.setInt(1,manufacturerID);
                statementManufacturer.setString(2,manufacturerName);

                statementManufacturer.execute();

            } catch (SQLException e) {
                System.out.println("Manufacturer (addManufacturerToDatabase()) has not been connected to database");
                throw new RuntimeException(e);

            } finally {
                JetSysDatabase.closeConnection(connectManufacturer,statementManufacturer,resultManufacturer);
            }
    }
}
