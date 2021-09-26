package com.bridgeLabz.employeePayroll;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

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

    public List<EmployeePayRollData> readData() throws SQLException {

        String sql = "Select * from employee";
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            LocalDate date = resultSet.getDate("start").toLocalDate();
            employeePayRollDataList.add(new EmployeePayRollData(id, name, salary, date));

        }

        return employeePayRollDataList;
    }

    public int updateEmployeeData(String name, double salary) {
        String sql = String.format("update employee set salary = %.2f where name = '%s';", salary, name);
        System.out.println(sql);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
