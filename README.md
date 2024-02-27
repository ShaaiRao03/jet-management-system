# JETCRAFT : Private Jet Management System 

![JETCRAFT](src/main/resources/img/readME/inventory.png)

JETCRAFT is a robust full-stack Private Jet Management System developed using Java (with JavaFX frontend) and MYSQL. It offers a seamless user experience for administrators and clients, with features designed to streamline operations and enhance user interaction.

Inspired by [Jetcraft](https://www.jetcraft.com/inventory/), a leading platform in private aviation, JETCRAFT aims to replicate its functionalities while adding enhancements and customizations. 

## Features

### Admin Features:
- **Manage Manufacturers, Models, and Private Jets:**
  - Search, add, update, and delete manufacturers, models, and private jets. 
- **Transaction Management:**
  - View both pending transactions and approved ones.
  - Approve or reject pending transactions.
- **Invoice Management:**
  - Download invoices for company reference.
- **Data Insights:**
  - View dashboard for visualized data insights.

### Client Features:
- **Inventory Exploration:**
  - Explore inventory through searching or filtering.
  - View detailed information about jets.
- **Application Submission:**
  - Fill out application forms for purchasing jets.
- **Application Status:**
  - Check the status of their application.
  - Preview their submitted applications.
- **Transaction Management:**
  - View transaction history.
  - Download auto-generated invoices for their transactions.

## Run Locally

1) Clone the project 

```bash
  git clone https://github.com/ShaaiRao03/jet-management-system.git
```

2) Locate "jet_enterprise_ShaaiRao.sql"

3) Import the contents of the "jet_enterprise_ShaaiRao.sql" into your database (in this project, phpMyAdmin has been used)

4) Locate "JetSysDatabase" class and in the "getConnection()" method, change the username and password based on your setup. (the current username and password is the default version)

5) No JAR file need to imported as it has already been included in the lib folder. 

6) Run the project  

7) You can either Sign Up and then Log In OR refer to the "logindetailsjet" table in the database for login credentials. 

If you face any issues, please refer to the "Possible Issues" section for clarification. 

## Possible Issues

### 1) Issues with the JAR file in the lib folder

- Download the latest ZIP file version from [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) and [iTextPDF](https://mvnrepository.com/artifact/com.itextpdf/itextpdf)
- Extract the zip files
- Create a directory called “lib” in the project you’re working on (only if it doesn't exist)
- Import the JAR files into the “lib” directory (drag and drop works)
- Go to `Settings` → `Project Structure` → `Modules` → `Dependencies`
- Add the JAR files there and click `Apply`, then click `Ok`

### 2) Error: JavaFX runtime components are missing, and are required to run this application

- Download the SDK from [JavaFX](https://gluonhq.com/products/javafx/)
- Navigate to `C:\Program Files\Java\`
- Paste the unzipped folder (`javafx-sdk-21.0.2`)
- Navigate to `lib` within the SDK and copy the address `C:\Program Files\Java\javafx-sdk-21.0.2\lib`
- In IntelliJ, navigate to `Run` → `Edit Configuration`
- Click on `Modify options` at the top right, and check the checkbox for `Add VM Options`
- Paste `--module-path "YOUR-FILE-PATH-TO-SDK-LIB" --add-modules javafx.controls,javafx.fxml` in the VM argument
- Run the program


