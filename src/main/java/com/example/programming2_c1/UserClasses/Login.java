package com.example.programming2_c1.UserClasses;

import com.example.programming2_c1.JetSysDatabase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecurityQues() {
        return securityQues;
    }

    public void setSecurityQues(String securityQues) {
        this.securityQues = securityQues;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    String userName;
    String securityQues;
    String answer;

    public static Login getLoginDetailsGivenUsername(String username ) {
        Connection connectLogin = null;
        ResultSet resultLogin = null;
        PreparedStatement statementLogin = null;

        Login login = null;

        String login_query = "SELECT * FROM logindetailsjet WHERE username = ?";
        connectLogin = JetSysDatabase.getConnection();

        try {

            statementLogin = connectLogin.prepareStatement(login_query);
            statementLogin.setString(1, username);

            resultLogin = statementLogin.executeQuery();

            while (resultLogin.next()) {
                login = new Login(resultLogin.getString("userName"), resultLogin.getString("security_ques"),resultLogin.getString("answer"));
            }

        }catch (Exception e){
            System.out.println("LoginController (getLoginDetailsGivenUsername()) has not been connected to database");

        } finally {
            JetSysDatabase.closeConnection(connectLogin,statementLogin,resultLogin);
        }
        return login;
    }

    public static void updateLoginJet(String prevUsername, String passwordNew) throws InterruptedException {

            Connection connectLogin = null;
            ResultSet resultLogin = null;
            PreparedStatement statementLogin = null;

            try {
                String updateUser_query = "UPDATE logindetailsjet SET password = ? WHERE userName = ?; ";
                connectLogin = JetSysDatabase.getConnection();

                statementLogin = connectLogin.prepareStatement(updateUser_query);
                statementLogin.setString(1,passwordNew);
                statementLogin.setString(2,prevUsername);

                statementLogin.execute();

            }catch (Exception e){
                System.out.println("LoginController (updateLoginJet()) has not been connected to database");

            } finally {
                JetSysDatabase.closeConnection(connectLogin,statementLogin,resultLogin);
            }
    }

    public static void insertToLoginDetails(String username , String password , String securityques , String answer, String role) throws InterruptedException {

        Connection connectLogin = null;
        ResultSet resultLogin = null;
        PreparedStatement statementLogin = null;

        try {
            String insertUser_query = " INSERT INTO logindetailsjet VALUES ( ? , ? , ? , ? , ?) ";
            connectLogin = JetSysDatabase.getConnection();

            statementLogin = connectLogin.prepareStatement(insertUser_query);
            statementLogin.setString(1,username);
            statementLogin.setString(2,password);
            statementLogin.setString(3,securityques);
            statementLogin.setString(4,answer);
            statementLogin.setString(5,role);

            statementLogin.execute();

        }catch (Exception e){
            System.out.println("LoginController (insertLogin()) has not been connected to database");
            e.printStackTrace();

        } finally {
            JetSysDatabase.closeConnection(connectLogin,statementLogin,resultLogin);
        }

    }

    public Login(){}

    public Login (String username, String securityQuestion, String answer){
        this.userName = username;
        this.securityQues = securityQuestion;
        this.answer = answer;
    }

}
