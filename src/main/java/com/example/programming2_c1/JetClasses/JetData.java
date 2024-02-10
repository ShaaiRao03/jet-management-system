package com.example.programming2_c1.JetClasses;


import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class JetData {

    public static ArrayList<Manufacturer> generateManufacturerList() throws InterruptedException {
        //to generate all manufacturers that is available in database
        ArrayList<Manufacturer> manufacturerList = new ArrayList<>();

        //thread is used here since there will be a situation where the amount of data to be retrieved is huge
        Thread manufacturerListgenerator = new Thread(() ->{
            Connection connectManufacturer = null;
            ResultSet resultManufacturer = null;
            PreparedStatement statementManufacturer = null;

            try {
                String obtainManufacturer = "SELECT * FROM manufacturer;";
                connectManufacturer = JetSysDatabase.getConnection();

                statementManufacturer = connectManufacturer.prepareStatement(obtainManufacturer);
                resultManufacturer = statementManufacturer.executeQuery();

                while (resultManufacturer.next()){
                    manufacturerList.add(new Manufacturer(resultManufacturer.getInt(1),resultManufacturer.getString(2)));
                }

            }catch (Exception e){
                System.out.println("JetData (generateManufacturerList())s has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectManufacturer,statementManufacturer,resultManufacturer);
            }
        }); manufacturerListgenerator.start(); manufacturerListgenerator.join();

        return manufacturerList;
    }


    public static  ArrayList<Model>  generateModelList() throws InterruptedException {
        //to generate all models that is available in database
        ArrayList<Model> modelList = new ArrayList<>();

        //thread is used here since there will be a situation where the amount of data to be retrieved is huge
        Thread modelListgenerator = new Thread(() ->{

            Connection connectModel = null;
            ResultSet resultModel = null;
            PreparedStatement statementModel = null;

            try {

                String obtainManufacturer = "SELECT * FROM model;";
                connectModel = JetSysDatabase.getConnection();

                statementModel = connectModel.prepareStatement(obtainManufacturer);
                resultModel = statementModel.executeQuery();

                while (resultModel.next()){
                    modelList.add(new Model(resultModel.getInt(1),resultModel.getInt(2),resultModel.getString(3)));
                }


            }catch (Exception e){
                System.out.println("JetData (generateModelList()) has not been connected to database");

            }finally {
                JetSysDatabase.closeConnection(connectModel,statementModel,resultModel);
            }

        }); modelListgenerator.start(); modelListgenerator.join();

        return modelList;
    }




    public static Manufacturer getManufacturerGivenID(int id) throws InterruptedException {

        ArrayList<Manufacturer> manufacturers = generateManufacturerList();

        for (Manufacturer temp:manufacturers) {
            if (id == temp.getManufacturerID()){
                return temp;
            }
        }

        return null;
    }

    public static Model getModelGivenID(int id) throws InterruptedException {

        ArrayList<Model> tempModelList = generateModelList();
        for (Model temp: tempModelList) {

            if (id == temp.getModelID()){
                return temp;
            }
        }
        return null;
    }

    public static PrivateJet getPrivateJetGivenID(String id) throws InterruptedException {

        ArrayList<PrivateJet> temp = getJetData();
        for (PrivateJet jet:temp) {

            if (jet.getJetID().equals(id)){
                return jet;
            }
        }
        return null;
    }


    public static ArrayList<PrivateJet> getJetData() throws  InterruptedException {
        //to get all private jet data
        ArrayList<PrivateJet> jet = new ArrayList<>();

        Thread jetDataGenerator = new Thread(()->{

            Connection connect = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try{

                PrivateJet jetTemp = new PrivateJet();

                String obtainJetDetails = "SELECT * FROM privatejet AS jet " +
                        "JOIN model USING(modeLID) " +
                        "JOIN airframe USING(jetID) " +
                        "JOIN exterior USING(jetID) " +
                        "JOIN interior USING(jetID) " +
                        "JOIN engine USING(jetID);";

                connect = JetSysDatabase.getConnection();

                preparedStatement = connect.prepareStatement(obtainJetDetails);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    String jetID = resultSet.getString("jetID");
                    JetExterior exterior = new JetExterior(jetID,resultSet.getString("stripe"),resultSet.getString("basepaint"));
                    JetInterior interior = new JetInterior(jetID,resultSet.getString("forwardCabinConfig"),resultSet.getString("AftCabinConfig"),resultSet.getInt("numPassengers"));
                    JetAirframe airframe = new JetAirframe(jetID,resultSet.getInt("timeSinceNew"),resultSet.getInt("landings"));
                    JetEngine engine = new JetEngine(jetID,resultSet.getInt("hoursSinceNew"),resultSet.getInt("cyclesSinceNew"),resultSet.getString("rightEngineID"),resultSet.getString("leftEngineID"),resultSet.getString("engineDescription"));

                    jetTemp = new PrivateJet(resultSet.getInt("modeLID"),exterior,interior,airframe,engine,jetID,resultSet.getInt(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getLong(7),resultSet.getInt(8));
                    jet.add(jetTemp);
                }

            }catch (Exception e){
                System.out.println("JetData (getJetData()) has not been connected to database");

            }finally {
                JetSysDatabase.closeConnection(connect,preparedStatement,resultSet);
            }

        }); jetDataGenerator.start(); jetDataGenerator.join();

        return jet;
    }


    public static List<String> manufacturerList(String role) throws InterruptedException {
        //Versatile manufacturerList
        //Can be used by both admin and client

        List<String> availableManufacturers = new ArrayList<>();
        ArrayList<Manufacturer> manufacturers = generateManufacturerList();

        for (Manufacturer key: manufacturers) {
            if (role.equals(CommonFunctions.client)){
                availableManufacturers.add(key.getManufacturerName());
            } else if (role.equals(CommonFunctions.admin)){
                availableManufacturers.add(key.getManufacturerID()+" - "+key.getManufacturerName());
            }
        }

        return availableManufacturers;
    }


    public static List<String> modelList(String manufacturerName, String role) throws InterruptedException{
        //Versatile modelList
        //Can be used by both admin and client

        ArrayList<Model> tempModelList = generateModelList();
        List<String> availableModels = new ArrayList<>();

        try {
            for (Model temp: tempModelList) {

                if (manufacturerName.equals(getManufacturerGivenID(temp.getManufacturerID()).getManufacturerName())) {
                    if (role.equals(CommonFunctions.client)) {
                        //used for searching query in inventory
                        availableModels.add(temp.getModelName());

                    } else if (role.equals(CommonFunctions.admin)) {
                        //used for searching query in private jet data operation
                        availableModels.add(temp.getModelID() + " - " + temp.getModelName());
                    }

                } else if (manufacturerName.equals("")){
                    //returns all models (used for private jet data operation)
                    availableModels.add(temp.getModelID()+" - "+temp.getModelName());
                }
            }

        }catch (NullPointerException | InterruptedException e){}

        return availableModels;
    }
}