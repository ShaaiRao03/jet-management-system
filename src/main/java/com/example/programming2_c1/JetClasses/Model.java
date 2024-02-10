package com.example.programming2_c1.JetClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {

    private int manufacturerID;
    private int modelID;
    private String modelName;

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }



    public Model(int manufacturerID, int modelID, String modelName) {
        this.modelID = modelID;
        this.modelName = modelName;
        this.manufacturerID = manufacturerID;
    }


    public static void addModelToDatabase(int manufacturerID, int modelID, String modelName){

        Connection connectModel = null;
        ResultSet resultModel = null;
        PreparedStatement statementModel = null;

        try {
            String jetInsert_query = "INSERT INTO model VALUES (?,?,?)";
            connectModel = JetSysDatabase.getConnection();

            statementModel = connectModel.prepareStatement(jetInsert_query);
            statementModel.setInt(1,manufacturerID);
            statementModel.setInt(2,modelID);
            statementModel.setString(3,modelName);

            statementModel.execute();

        } catch (SQLException e) {
            System.out.println("Model (addModeltoDatabase()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connectModel,statementModel,resultModel);
        }
    }


    public static void updateModel(int manufacturerID, int modelID, String modelName){

        Connection connectModel = null;
        ResultSet resultModel = null;
        PreparedStatement statementModel = null;

        try {
            String updateModel_query = "UPDATE `model` SET `modeLID`= ? ,`manufacturerID`= ? ,`modelName`=? WHERE `modeLID`= ?";
            connectModel = JetSysDatabase.getConnection();

            statementModel = connectModel.prepareStatement(updateModel_query);
            statementModel.setInt(1,modelID);
            statementModel.setInt(2,manufacturerID);
            statementModel.setString(3,modelName);
            statementModel.setInt(4,modelID);

            statementModel.execute();

        } catch (SQLException e) {
            System.out.println("Model (updateModel()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connectModel,statementModel,resultModel);
        }
    }


    public static void removeModelFromDatabase(int modelID){

        Connection connectModel = null;
        ResultSet resultModel = null;
        PreparedStatement statementModel = null;

        try {
            String modelDelete_query = "DELETE FROM model WHERE modeLID = ?";
            connectModel = JetSysDatabase.getConnection();

            statementModel = connectModel.prepareStatement(modelDelete_query);
            statementModel.setInt(1,modelID);

            statementModel.execute();

        }catch (Exception e){
            System.out.println("Model (removeModelFromDatabase()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectModel,statementModel,resultModel);
        }
    }
}
