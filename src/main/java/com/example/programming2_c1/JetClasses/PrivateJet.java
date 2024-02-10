package com.example.programming2_c1.JetClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class PrivateJet {

    private int modelID;
    private JetExterior exterior;
    private JetInterior interior;
    private JetAirframe airframe;
    private JetEngine engine;
    private String jetID;
    private String jetName;
    private int year;
    private String highlight1;
    private String highlight2;
    private String imgSrc;
    private long price;

    private int quantity;

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public JetExterior getExterior() {
        return exterior;
    }

    public void setExterior(JetExterior exterior) {
        this.exterior = exterior;
    }

    public JetInterior getInterior() {
        return interior;
    }

    public void setInterior(JetInterior interior) {
        this.interior = interior;
    }

    public JetAirframe getAirframe() {
        return airframe;
    }

    public void setAirframe(JetAirframe airframe) {
        this.airframe = airframe;
    }

    public JetEngine getEngine() {
        return engine;
    }

    public void setEngine(JetEngine engine) {
        this.engine = engine;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getJetName() {
        return jetName;
    }

    public void setJetName(int model, int Year) throws InterruptedException {

        try{
            this.jetName = year+" "+ Objects.requireNonNull(JetData.getManufacturerGivenID(Objects.requireNonNull(JetData.getModelGivenID(model)).getManufacturerID())).getManufacturerName()+" "+ Objects.requireNonNull(JetData.getModelGivenID(modelID)).getModelName();
        }catch (NullPointerException e){
            System.out.println("Private Jet setJetName(Model model, int Year) method null pointer exception ");
        }

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getJetID() {
        return jetID;
    }

    public void setJetID(String snValue) {
        this.jetID = snValue;
    }

    public String getHighlight1() {
        return highlight1;
    }

    public void setHighlight1(String highlight1) {
        this.highlight1 = highlight1;
    }

    public String getHighlight2() {
        return highlight2;
    }

    public void setHighlight2(String hightlight2) {
        this.highlight2 = hightlight2;
    }

    public PrivateJet(){}

    public PrivateJet(int modelID, JetExterior exterior, JetInterior interior, JetAirframe airframe, JetEngine engine, String jetID, int year, String highlight1, String highlight2, String imgSrc, long price, int quantity) throws InterruptedException {
        this.modelID = modelID;
        this.exterior = exterior;
        this.interior = interior;
        this.airframe = airframe;
        this.engine = engine;
        this.year = year;
        this.highlight1 = highlight1;
        this.highlight2 = highlight2;
        this.imgSrc = imgSrc;
        this.price = price;
        this.quantity = quantity;
        setJetID(jetID);
        setJetName(modelID,year);
    }

    public static void addPrivateJetToDatabase(int modelID, String jetID,int year, String highlight1,String highlight2, String imgSrc, long price, int quantity){

        Connection connectJet = null;
        ResultSet resultJet = null;
        PreparedStatement statementJet = null;

        try {
            String jetInsert_query = "INSERT INTO privatejet VALUES (?,?,?,?,?,?,?,?)";
            connectJet = JetSysDatabase.getConnection();

            statementJet = connectJet.prepareStatement(jetInsert_query);
            statementJet.setInt(1,modelID);
            statementJet.setString(2,jetID);
            statementJet.setInt(3,year);
            statementJet.setString(4,highlight1);
            statementJet.setString(5,highlight2);
            statementJet.setString(6,imgSrc);
            statementJet.setLong(7,price);
            statementJet.setInt(8,quantity);

            statementJet.execute();

        } catch (SQLException e) {
            System.out.println("Private (addPrivateJetToDatabase()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connectJet,statementJet,resultJet);
        }
    }

    public static void updateJet(int modelID, String jetID,int year, String highlight1,String highlight2, String imgSrc, long price, int quantity){

        Connection connectJet = null;
        ResultSet resultJet = null;
        PreparedStatement statementJet = null;

        try {
            String updateJet_query = "UPDATE `privatejet` SET `modeLID`= ? ,`jetID`= ? ,`year`=?,`highlight1`=?,`highlight2`=?,`image`=?,`price`=?,`quantity`=? WHERE `jetID`= ?";
            connectJet = JetSysDatabase.getConnection();

            statementJet = connectJet.prepareStatement(updateJet_query);
            statementJet.setInt(1,modelID);
            statementJet.setString(2,jetID);
            statementJet.setInt(3,year);
            statementJet.setString(4,highlight1);
            statementJet.setString(5,highlight2);
            statementJet.setString(6,imgSrc);
            statementJet.setLong(7,price);
            statementJet.setInt(8,quantity);
            statementJet.setString(9,jetID);

            statementJet.execute();

        } catch (SQLException e) {
            System.out.println("Private (updateJet()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connectJet,statementJet,resultJet);
        }
    }


    public static void removeJetFromDatabase(String jetID){

        Connection connectJet = null;
        ResultSet resultJet = null;
        PreparedStatement statementJet = null;

        try {
            String jetDelete_query = "DELETE FROM privatejet WHERE jetID = ?";
            connectJet = JetSysDatabase.getConnection();

            statementJet = connectJet.prepareStatement(jetDelete_query);
            statementJet.setString(1,jetID);

            statementJet.execute();

        }catch (Exception e){
            System.out.println("PrivateJet (removeJetFromDatabase()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectJet,statementJet,resultJet);
        }
    }


    public static void updateJetQuantity(PrivateJet jet, boolean isAdd, int value){

        Connection connectJet = null;
        ResultSet resultJet = null;
        PreparedStatement statementJet = null;

        int latestQuantity;

        if (isAdd){
            latestQuantity = jet.getQuantity() + value;
        } else {
            latestQuantity = jet.getQuantity() - value;
        }


        try {
            String updateJet_query = "UPDATE `privatejet` SET `quantity`=? WHERE `jetID`= ?";
            connectJet = JetSysDatabase.getConnection();

            statementJet = connectJet.prepareStatement(updateJet_query);
            statementJet.setInt(1,latestQuantity);
            statementJet.setString(2,jet.getJetID());


            statementJet.execute();

        } catch (SQLException e) {
            System.out.println("Private (updateJetQuantity()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connectJet,statementJet,resultJet);
        }
    }

    public String toString() {
        return jetName;
    }

}
