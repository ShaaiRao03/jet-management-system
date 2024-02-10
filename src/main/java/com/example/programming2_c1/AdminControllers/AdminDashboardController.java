package com.example.programming2_c1.AdminControllers;

import com.example.programming2_c1.CommonFunctions;
import com.example.programming2_c1.JetSysDatabase;
import com.example.programming2_c1.UserClasses.Login;
import com.example.programming2_c1.UserClasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {


    @FXML
    private LineChart<String, Long> lineChartByYear;

    @FXML
    private CategoryAxis xAxisChart;


    @FXML
    private NumberAxis yAxisChart;

    @FXML
    private ComboBox<String> yearCombo;

    @FXML
    private TextField addressAdmin;

    @FXML
    private AnchorPane adminProfileAnchor;

    @FXML
    private Button editAdminBtn;

    @FXML
    private TextField emailAdmin;

    @FXML
    private TextField f_nameAdmin;

    @FXML
    private TextField l_nameAdmin;

    @FXML
    private Button adminProfileBtn;

    @FXML
    private Button visualReportBtn;

    @FXML
    private BarChart<String, Integer> manufacturerBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PasswordField passwordAdmin;

    @FXML
    private TextField phoneNumAdmin;

    @FXML
    private Label totalJetsLabel;

    @FXML
    private Label totalRevenueLabel;

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Button updateAdminBtn;

    @FXML
    private TextField usernameAdmin;

    @FXML
    private TextField validationPasswordAdmin;

    @FXML
    private AnchorPane visualReportAnchor;

    public static AdminInterfaceController adminInterfaceController;
    String prevUsername;

    ObservableList<String> years = FXCollections.observableArrayList(
            "2021","2022","2023"
    );


    public void setAdminDashboardAnchor(boolean visualReportAnchor, boolean adminProfileAnchor){
        this.visualReportAnchor.setVisible(visualReportAnchor);
        this.adminProfileAnchor.setVisible(adminProfileAnchor);
    }

    // PROFILE METHODS ----------------------------------------------
    public void resetAdminClass(){
        adminInterfaceController.getUser().setAddress(addressAdmin.getText());
        adminInterfaceController.getUser().setEmail(emailAdmin.getText());
        adminInterfaceController.getUser().setFirst_name(f_nameAdmin.getText());
        adminInterfaceController.getUser().setLast_name(l_nameAdmin.getText());
        adminInterfaceController.getUser().setPassword(passwordAdmin.getText());
        adminInterfaceController.getUser().setUserName(usernameAdmin.getText());
        adminInterfaceController.getUser().setPhoneNum(phoneNumAdmin.getText());
    }


    public void setAllField(){
        addressAdmin.setText(adminInterfaceController.getUser().getAddress());
        emailAdmin.setText(adminInterfaceController.getUser().getEmail());
        f_nameAdmin.setText(adminInterfaceController.getUser().getFirst_name());
        l_nameAdmin.setText(adminInterfaceController.getUser().getLast_name());
        passwordAdmin.setText(adminInterfaceController.getUser().getPassword());
        usernameAdmin.setText(adminInterfaceController.getUser().getUserName());
        phoneNumAdmin.setText(String.valueOf(adminInterfaceController.getUser().getPhoneNum()));
    }

    public void statusFieldAdmin(boolean status){
        addressAdmin.setDisable(status);
        emailAdmin.setDisable(status);
        f_nameAdmin.setDisable(status);
        l_nameAdmin.setDisable(status);
        passwordAdmin.setDisable(status);
        usernameAdmin.setDisable(status);
        phoneNumAdmin.setDisable(status);
    }

    public void setValidatorComponents(boolean editBtn, boolean updateBtn, boolean validationPassword){
        updateAdminBtn.setDisable(updateBtn);
        editAdminBtn.setDisable(editBtn);
        validationPasswordAdmin.setDisable(validationPassword);
    }

    public boolean checkIsEmpty(){
        return f_nameAdmin.getText().isEmpty() || l_nameAdmin.getText().isEmpty() || usernameAdmin.getText().isEmpty() || passwordAdmin.getText().isEmpty() || addressAdmin.getText().isEmpty() || emailAdmin.getText().isEmpty() || phoneNumAdmin.getText().isEmpty() ;
    }

    // VISUAL REPORT METHODS ---------------------------------------------------

    LinkedHashMap<String , Long> monthToRevenue = new LinkedHashMap<>(){{
        put("January",0L);
        put("February",0L);
        put("March",0L);
        put("April",0L);
        put("May",0L);
        put("June",0L);
        put("July",0L);
        put("August",0L);
        put("September",0L);
        put("October",0L);
        put("November",0L);
        put("December",0L);
    }};

    public void generateLineChartRevenue(String year){

        monthToRevenue.replaceAll((k, v) -> 0L);

        Connection connectDatabase = null;
        ResultSet resultDatabase = null;
        PreparedStatement statementDatabase = null;

        lineChartByYear.getData().clear();

        //setting the range of y-axis
        yAxisChart.setAutoRanging(false);
        yAxisChart.setLowerBound(0);
        yAxisChart.setUpperBound(200000000);
        yAxisChart.setTickUnit(10000000);

        try {

            XYChart.Series<String,Long> chartRevenue = new XYChart.Series<>();
            chartRevenue.setName("Months");

            String obtainRevenueByYear = "SELECT MONTH(transactionDate) as Month,SUM(totalAmount) FROM transaction WHERE YEAR(transactionDate) = ? GROUP BY MONTH(transactionDate);";

            connectDatabase = JetSysDatabase.getConnection();

            statementDatabase = connectDatabase.prepareStatement(obtainRevenueByYear);
            statementDatabase.setString(1,year);
            resultDatabase = statementDatabase.executeQuery();

            while (resultDatabase.next()) {

                String month = resultDatabase.getString("Month");

                switch (month){

                    case "1":
                        monthToRevenue.put("January",resultDatabase.getLong(2));
                        break;

                    case "2":
                        monthToRevenue.put("February",resultDatabase.getLong(2));
                    break;

                    case "3":
                        monthToRevenue.put("March",resultDatabase.getLong(2));
                        break;

                    case "4":
                        monthToRevenue.put("April",resultDatabase.getLong(2));
                        break;

                    case "5":
                        monthToRevenue.put("May",resultDatabase.getLong(2));
                        break;

                    case "6":
                        monthToRevenue.put("June",resultDatabase.getLong(2));
                        break;

                    case "7":
                        monthToRevenue.put("July",resultDatabase.getLong(2));
                        break;

                    case "8":
                        monthToRevenue.put("August",resultDatabase.getLong(2));
                        break;

                    case "9":
                        monthToRevenue.put("September",resultDatabase.getLong(2));
                        break;

                    case "10":
                        monthToRevenue.put("October",resultDatabase.getLong(2));
                        break;

                    case "11":
                        monthToRevenue.put("November",resultDatabase.getLong(2));
                        break;

                    case "12":
                        monthToRevenue.put("December",resultDatabase.getLong(2));
                        break;

                }
            }

            for (String key : monthToRevenue.keySet()){
                chartRevenue.getData().add(new XYChart.Data<>(key,monthToRevenue.get(key)));
            }

            xAxisChart.setAnimated(false);
            lineChartByYear.getData().add(chartRevenue);

        }catch (Exception e){
            System.out.println("AdminDashboardController (generateLineChartRevenue()) has not been connected to database");

        }finally {
            JetSysDatabase.closeConnection(connectDatabase,statementDatabase,resultDatabase);
        }

    }



    public void generateBarChartManufacturerPrivateJet(){
        Connection connectDatabase = null;
        ResultSet resultDatabase = null;
        PreparedStatement statementDatabase = null;

        manufacturerBarChart.getData().clear();

        //setting the range of y-axis
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(30);
        yAxis.setTickUnit(10);

        XYChart.Series<String,Integer> chart = new XYChart.Series<>();
        chart.setName("Manufacturers");

        try {

            String obtainManufacturerPrivateJetCount = "SELECT manufacturerName,COUNT(manufacturerID) FROM model \n" +
                    "JOIN manufacturer USING (manufacturerID) \n" +
                    "JOIN privatejet USING (modeLID)\n" +
                    "GROUP BY manufacturerName";


            connectDatabase = JetSysDatabase.getConnection();

            statementDatabase = connectDatabase.prepareStatement(obtainManufacturerPrivateJetCount);
            resultDatabase = statementDatabase.executeQuery();

            while (resultDatabase.next()) {
                chart.getData().add(new XYChart.Data<>(resultDatabase.getString(1), resultDatabase.getInt(2)));
            }

            xAxis.setAnimated(false);
            manufacturerBarChart.getData().add(chart);

        }catch (Exception e){
            System.out.println("AdminDashboardController (generateBarChartManufacturerPrivateJet()) has not been connected to database");

        }finally {
            JetSysDatabase.closeConnection(connectDatabase,statementDatabase,resultDatabase);
        }
    }


    public void generateTotalUsers(){
        Connection connectDatabase1 = null;
        ResultSet resultDatabase1 = null;
        PreparedStatement statementDatabase1 = null;

        try {

            String obtainUserCount = "SELECT COUNT(userName) FROM `userjet` ";

            connectDatabase1 = JetSysDatabase.getConnection();

            statementDatabase1 = connectDatabase1.prepareStatement(obtainUserCount);
            resultDatabase1 = statementDatabase1.executeQuery();

            while (resultDatabase1.next()) {
                totalUsersLabel.setText(resultDatabase1.getString(1));
            }


        }catch (Exception e){
            System.out.println("AdminDashboardController (generateTotalUsers()) has not been connected to database");

        }finally {
            JetSysDatabase.closeConnection(connectDatabase1,statementDatabase1,resultDatabase1);
        }

    }


    public void generateTotalJets(){
        Connection connectDatabase2 = null;
        ResultSet resultDatabase2 = null;
        PreparedStatement statementDatabase2 = null;

        try {

            String obtainJetCount = "SELECT COUNT(jetID) FROM `privatejet` ";

            connectDatabase2 = JetSysDatabase.getConnection();

            statementDatabase2 = connectDatabase2.prepareStatement(obtainJetCount);
            resultDatabase2 = statementDatabase2.executeQuery();

            while (resultDatabase2.next()) {
                totalJetsLabel.setText(resultDatabase2.getString(1));
            }


        }catch (Exception e){
            System.out.println("AdminDashboardController (generateTotalJets()) has not been connected to database");

        }finally {
            JetSysDatabase.closeConnection(connectDatabase2,statementDatabase2,resultDatabase2);
        }
    }



    public void generateTotalRevenue(){
        Connection connectDatabase3 = null;
        ResultSet resultDatabase3 = null;
        PreparedStatement statementDatabase3 = null;

        try {

            String obtainRevenueTotal = "SELECT SUM(totalAmount) FROM transaction ";

            connectDatabase3 = JetSysDatabase.getConnection();

            statementDatabase3 = connectDatabase3.prepareStatement(obtainRevenueTotal);
            resultDatabase3 = statementDatabase3.executeQuery();

            while (resultDatabase3.next()) {
                NumberFormat nf = NumberFormat.getInstance();
                totalRevenueLabel.setText(nf.format(Integer.parseInt(resultDatabase3.getString(1))));
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("AdminDashboardController (generateTotalRevenue()) has not been connected to database");

        }finally {
            JetSysDatabase.closeConnection(connectDatabase3,statementDatabase3,resultDatabase3);
        }
    }

    // -------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateLineChartRevenue("2023");

        yearCombo.setItems(years);
        yearCombo.setValue("2023");

        adminInterfaceController.getScrollPaneAdmin().setVvalue(0.0);
        adminInterfaceController.getScrollPaneAdmin().setVmax(0.4);

        //Start profile first
        setAdminDashboardAnchor(false,true);
        statusFieldAdmin(true);
        setAllField();
        updateAdminBtn.setDisable(true);

        yearCombo.setOnAction(e -> {
            generateLineChartRevenue(yearCombo.getValue());
        });

        visualReportBtn.setOnAction(e -> {
            adminInterfaceController.getScrollPaneAdmin().setVvalue(0.0);
            adminInterfaceController.getScrollPaneAdmin().setVmax(1.0);
            adminInterfaceController.setNavigationLabel("Dashboard  /  Visual Report");
            setAdminDashboardAnchor(true,false);
            generateBarChartManufacturerPrivateJet();
            generateTotalUsers();
            generateTotalJets();
            generateTotalRevenue();
        });

        adminProfileBtn.setOnAction(e -> {
            adminInterfaceController.getScrollPaneAdmin().setVvalue(0.0);
            adminInterfaceController.getScrollPaneAdmin().setVmax(0.4);
            adminInterfaceController.setNavigationLabel("Dashboard  /  Profile");
            setAdminDashboardAnchor(false,true);
            statusFieldAdmin(true);
            setAllField();
            updateAdminBtn.setDisable(true);
        });

        // PROFILE PART----------------------------------------------------

        editAdminBtn.setOnAction(e -> {
            prevUsername = usernameAdmin.getText();
            if (validationPasswordAdmin.getText().equals(adminInterfaceController.getUser().getPassword())){
                statusFieldAdmin(false);

                setValidatorComponents(true,false,true);
                validationPasswordAdmin.clear();
                validationPasswordAdmin.setPromptText("Enter password to edit");

            } else {
                CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error Message","Incorrect password");
            }
        });


        updateAdminBtn.setOnAction(e -> {

            if (checkIsEmpty()){
                CommonFunctions.createAlert(Alert.AlertType.ERROR,"Error Message","Please fill in all the blank fields");

            } else {

                if (CommonFunctions.validationLong(phoneNumAdmin.getText(),"Invalid input for 'Phone number field'")){
                    try {
                        User.updateUserJet(usernameAdmin.getText(), f_nameAdmin.getText(), l_nameAdmin.getText(),emailAdmin.getText(),phoneNumAdmin.getText(),
                                addressAdmin.getText(),prevUsername);

                        Login.updateLoginJet(prevUsername,passwordAdmin.getText());

                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    setValidatorComponents(false,true,false);
                    resetAdminClass();
                    setAllField();
                    statusFieldAdmin(true);
                    CommonFunctions.createAlert(Alert.AlertType.INFORMATION,"Successful","Update Successful !"); //CLEANUP

                }

            }
        });



        //----------------------------------------------------------

    }
}
