package com.example.programming2_c1.UserClasses;

import com.example.programming2_c1.JetSysDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin extends User{

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        salary = salary;
    }

    int salary;

    public Admin(String userName, String identity_card, String first_name, String last_name, String email, String phoneNum,
                 String address, String country, String role, String password, int salary){

        super(userName,identity_card,first_name, last_name, email, phoneNum, address, country, role, password);
        this.salary = salary;
    }

    public Admin(){}

    public static Admin getAdminGivenUsername(String username) {

        Admin admin = new Admin();
        Connection connectAdmin = null;
        ResultSet resultAdmin = null;
        PreparedStatement statementAdmin = null;

        try {

            String obtainAdmin = "SELECT * FROM admin JOIN userjet USING(userName) JOIN logindetailsjet USING(userName) WHERE userName = ? ;";
            connectAdmin = JetSysDatabase.getConnection();

            statementAdmin = connectAdmin.prepareStatement(obtainAdmin);
            statementAdmin.setString(1,username);
            resultAdmin = statementAdmin.executeQuery();

            while (resultAdmin.next()){
                admin =  new Admin(resultAdmin.getString("userName"),resultAdmin.getString("identity_card"),resultAdmin.getString("first_name"),
                        resultAdmin.getString("last_name"),resultAdmin.getString("email"),
                        resultAdmin.getString("phoneNum"),resultAdmin.getString("address"),resultAdmin.getString("country"),resultAdmin.getString("role"),resultAdmin.getString("password"), resultAdmin.getInt("salary"));
            }


        }catch (Exception e){
            System.out.println("Admin (getClientGivenUsername()) has not been connected to database");

        }finally {
            JetSysDatabase.closeConnection(connectAdmin,statementAdmin,resultAdmin);
        }

        return admin;
    }
}
