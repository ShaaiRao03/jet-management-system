//This file will store all the methods for sql that are reusable

package com.example.programming2_c1;
import com.example.programming2_c1.UserClasses.User;
import java.sql.*;

public class JetSysDatabase {

    public static void main(String[] args) {
        getConnection();
    }

    public static Connection getConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //setting up the driver class
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/jet_enterprise","root" , ""); //obtaining connection instance
            return connect;

        } catch (Exception e) {
            System.out.println("Database has not been connected. Please make sure Apache and MySQL has been started in Xampp Control Panel");
        }
        return null;
    }

    public static void closeConnection(Connection connection , PreparedStatement preparedStatement , ResultSet resultSet){

        try{
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }



}