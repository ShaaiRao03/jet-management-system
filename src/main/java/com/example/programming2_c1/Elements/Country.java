package com.example.programming2_c1.Elements;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Country {

    public static String getCountryID(String countryName){

        String isoCode = null;
        Connection connectCountry = null;
        ResultSet resultCountry = null;
        PreparedStatement statementCountry = null;

        try {
            String obtainCountry = "SELECT isoCode FROM countrydelivery WHERE countryName = ?;";
            connectCountry = JetSysDatabase.getConnection();

            statementCountry = connectCountry.prepareStatement(obtainCountry);
            statementCountry.setString(1,countryName);
            resultCountry = statementCountry.executeQuery();

            while(resultCountry.next()){
                isoCode = resultCountry.getString(1);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Country (getCountryID()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectCountry,statementCountry,resultCountry);
        }

        return isoCode;
    }

    public static String getCountryName(String countryID){

        String countryName = "";
        Connection connectCountry = null;
        ResultSet resultCountry = null;
        PreparedStatement statementCountry = null;

        try {
            String obtainCountry = "SELECT countryName FROM countrydelivery WHERE  isoCode = ?;";
            connectCountry = JetSysDatabase.getConnection();

            statementCountry = connectCountry.prepareStatement(obtainCountry);
            statementCountry.setString(1,countryID);
            resultCountry = statementCountry.executeQuery();

            while(resultCountry.next()){
                countryName = resultCountry.getString(1);
            }

        }catch (Exception e){
            System.out.println("Country (getCountryName()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectCountry,statementCountry,resultCountry);
        }

        return countryName;
    }

}
