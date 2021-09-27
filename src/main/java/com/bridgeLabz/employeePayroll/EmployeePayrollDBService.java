package com.bridgeLabz.employeePayroll;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    PreparedStatement employeePayrollDataService;
    private static EmployeePayrollDBService employeePayrollDBService;

    private EmployeePayrollDBService() {
    }

    public static EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();

        return employeePayrollDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "ajaybarath1999";
        Connection connection;

       System.out.println("Connecting to database : " + jdbcURL);
       connection = DriverManager.getConnection(jdbcURL, userName, password);
       System.out.println("Connection is successfull : " + connection);
       return connection;

    }

    public List<EmployeePayRollData> readData() throws EmployeePayrollException {


        try {
            String sql = "Select * from employee";
            List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
            Connection connection = getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayRollDataList = this.getEmployeePayrollData(resultSet);

            return employeePayRollDataList;
        } catch (SQLException throwables) {
            throw new EmployeePayrollException("Check db connectivity");
        }


    }

    public int updateEmployeeData(String name, double salary) throws EmployeePayrollException {
        String sql = String.format("update employee set salary = %.2f where name = '%s';", salary, name);
        System.out.println(sql);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throw new EmployeePayrollException("Check db connectivity");
        }
    }

    public List<EmployeePayRollData> getEmployeePayrollData(String name) throws EmployeePayrollException {
        List<EmployeePayRollData> employeePayRollDataList = null;
        if (this.employeePayrollDataService == null) {
            this.prepareStatementForEmployeeData();
        }

        try {
            employeePayrollDataService.setString(1, name);
            ResultSet resultSet = employeePayrollDataService.executeQuery();
            employeePayRollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException throwables) {
            throw new EmployeePayrollException(throwables.getMessage());
        }

        return employeePayRollDataList;
    }

    private List<EmployeePayRollData> getEmployeePayrollData(ResultSet resultSet) throws EmployeePayrollException {
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate date = resultSet.getDate("start").toLocalDate();
                employeePayRollDataList.add(new EmployeePayRollData(id, name, salary, date));
            }
        } catch (SQLException throwables) {
            throw new EmployeePayrollException(throwables.getMessage());
        }
        return employeePayRollDataList;
    }

    private void prepareStatementForEmployeeData() throws EmployeePayrollException {
        try {
            Connection connection = this.getConnection();
            String sql = "select * from employee where name = ?";
            employeePayrollDataService = connection.prepareStatement(sql);

        } catch (SQLException throwables) {
            throw new EmployeePayrollException(throwables.getMessage());
        }
    }

    public List<EmployeePayRollData> getEmployeePayrollDataByDateRange(String startDate, String endDate) throws EmployeePayrollException {

        try {
            String sql = "Select * from employee where start between '" + startDate + "' and '" + endDate + "'";
            List<EmployeePayRollData> employeePayRollDataList;
            Connection connection = getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayRollDataList = this.getEmployeePayrollData(resultSet);

            return employeePayRollDataList;
        } catch (SQLException throwables) {
            throw new EmployeePayrollException(throwables.getMessage());
        }

    }
}
