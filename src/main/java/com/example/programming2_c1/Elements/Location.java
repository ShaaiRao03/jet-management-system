package com.example.programming2_c1.Elements;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Location {

    public static String getLocationID(String airportName){

        String locationID = "";
        Connection connectAirport= null;
        ResultSet resultAirport = null;
        PreparedStatement statementAirport = null;

        try {
            String obtainLocation = "SELECT airportCode FROM `airports` WHERE airportName = ? ;";
            connectAirport = JetSysDatabase.getConnection();

            statementAirport = connectAirport.prepareStatement(obtainLocation);
            statementAirport.setString(1,airportName);
            resultAirport = statementAirport.executeQuery();

            while(resultAirport.next()){
                locationID = resultAirport.getString(1);
            }

        }catch (Exception e){
            System.out.println("Location (getLocationID()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectAirport,statementAirport,resultAirport);
        }
        return locationID;
    }


    public static String getLocationName(String airportCode){

        String locationName = "";
        Connection connectAirport= null;
        ResultSet resultAirport = null;
        PreparedStatement statementAirport = null;

        try {
            String obtainLocation = "SELECT airportName FROM `airports` WHERE airportCode = ? ;";
            connectAirport = JetSysDatabase.getConnection();

            statementAirport = connectAirport.prepareStatement(obtainLocation);
            statementAirport.setString(1,airportCode);
            resultAirport = statementAirport.executeQuery();

            while(resultAirport.next()){
                locationName = resultAirport.getString(1);
            }

        }catch (Exception e){
            System.out.println("Location (getLocationName()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectAirport,statementAirport,resultAirport);
        }
        return locationName;
    }

}
