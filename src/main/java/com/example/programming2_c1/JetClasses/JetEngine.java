package com.example.programming2_c1.JetClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JetEngine {

    private String jetID;
    private int engineID;
    private int hoursSinceNew;
    private int cycleSinceNew;
    private String rightEngineID;
    private String leftEngineID;
    private String engineDescription;

    public String getJetID() {
        return jetID;
    }

    public void setJetID(String jetID) {
        this.jetID = jetID;
    }

    public int getEngineID() {
        return engineID;
    }

    public void setEngineID(int engineID) {
        this.engineID = engineID;
    }

    public int getHoursSinceNew() {
        return hoursSinceNew;
    }

    public void setHoursSinceNew(int hoursSinceNew) {
        this.hoursSinceNew = hoursSinceNew;
    }

    public int getCycleSinceNew() {
        return cycleSinceNew;
    }

    public void setCycleSinceNew(int cycleSinceNew) {
        this.cycleSinceNew = cycleSinceNew;
    }

    public String getRightEngineID() {
        return rightEngineID;
    }

    public void setRightEngineID(String rightEngineID) {
        this.rightEngineID = rightEngineID;
    }

    public String getLeftEngineID() {
        return leftEngineID;
    }

    public void setLeftEngineID(String leftEngineID) {
        this.leftEngineID = leftEngineID;
    }

    public String getEngineDescription() {
        return engineDescription;
    }

    public void setEngineDescription(String engineDescription) {
        this.engineDescription = engineDescription;
    }

    public JetEngine(){};

    public JetEngine(String jetID, int hoursSinceNew, int cycleSinceNew, String rightEngineID, String leftEngineID, String engineDescription) {
        this.jetID = jetID;
        this.hoursSinceNew = hoursSinceNew;
        this.cycleSinceNew = cycleSinceNew;
        this.rightEngineID = rightEngineID;
        this.leftEngineID = leftEngineID;
        this.engineDescription = engineDescription;
    }


    public static void addEngineToDatabase(String jetID, int hoursSinceNew , int cyclesSinceNew, String rightEngineID, String leftEngineID, String engineDescription){

        Connection connnectEngine = null;
        ResultSet resultEngine = null;
        PreparedStatement statementEngine = null;

        try {
            String engineInsert_query = "INSERT INTO `engine` VALUES (?,?,?,?,?,?)";
            connnectEngine = JetSysDatabase.getConnection();

            statementEngine = connnectEngine.prepareStatement(engineInsert_query);
            statementEngine.setString(1,jetID);
            statementEngine.setInt(2,hoursSinceNew);
            statementEngine.setInt(3,cyclesSinceNew);
            statementEngine.setString(4,rightEngineID);
            statementEngine.setString(5,leftEngineID);
            statementEngine.setString(6,engineDescription);

            statementEngine.execute();

        } catch (SQLException e) {
            System.out.println("Engine (addEngineToDatabase()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connnectEngine,statementEngine,resultEngine);
        }
    }

    public static void updateEngine(String jetID, int hoursSinceNew , int cyclesSinceNew, String rightEngineID, String leftEngineID, String engineDescription){

        Connection connnectEngine = null;
        ResultSet resultEngine = null;
        PreparedStatement statementEngine = null;

        try {
            String updateEngine_query = "UPDATE `engine` SET `jetID`=?,`hoursSinceNew`=?,`cyclesSinceNew`=?,`rightEngineID`=?,`leftEngineID`=?,`engineDescription`=? WHERE `jetID`=?";
            connnectEngine = JetSysDatabase.getConnection();

            statementEngine = connnectEngine.prepareStatement(updateEngine_query);
            statementEngine.setString(1,jetID);
            statementEngine.setInt(2,hoursSinceNew);
            statementEngine.setInt(3,cyclesSinceNew);
            statementEngine.setString(4,rightEngineID);
            statementEngine.setString(5,leftEngineID);
            statementEngine.setString(6,engineDescription);
            statementEngine.setString(7,jetID);

            statementEngine.execute();

        } catch (SQLException e) {
            System.out.println("Engine (updateEngine()) has not been connected to database");
            throw new RuntimeException(e);

        } finally {
            JetSysDatabase.closeConnection(connnectEngine,statementEngine,resultEngine);
        }
    }

}
