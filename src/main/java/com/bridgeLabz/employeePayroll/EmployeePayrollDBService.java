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

    public List<EmployeePayRollData> getSumOfEmployeeSalary() throws EmployeePayrollException {
        try {
            String sql = "select gender, sum(salary) from employee group by gender;";
            List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
            Connection connection = getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double salary = resultSet.getDouble("sum(salary)");
                String gender = resultSet.getString("gender");
                employeePayRollDataList.add(new EmployeePayRollData(salary, gender));
            }
            return employeePayRollDataList;
        } catch (SQLException throwables) {
            throw new EmployeePayrollException(throwables.getMessage());
        }
    }

    public List<EmployeePayRollData> getAvgSumMinMaxEmployeeSalary(String data) throws EmployeePayrollException {
        try {
            String sql = "select gender, " + data + "(salary) from employee group by gender;";
            List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
            Connection connection = getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double salary = resultSet.getDouble(data + "(salary)");
                String gender = resultSet.getString("gender");
                employeePayRollDataList.add(new EmployeePayRollData(salary, gender));
            }
            return employeePayRollDataList;
        } catch (SQLException throwables) {
            throw new EmployeePayrollException(throwables.getMessage());
        }
    }

    public List<EmployeePayRollData> getEmployeeCountByGender() throws EmployeePayrollException {
        try {
            String sql = "select gender, count(gender) from employee group by gender;";
            List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
            Connection connection = getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                double salary = resultSet.getDouble("count(gender)");
                String gender = resultSet.getString("gender");
                employeePayRollDataList.add(new EmployeePayRollData(salary, gender));
            }
            return employeePayRollDataList;
        } catch (SQLException throwables) {
            throw new EmployeePayrollException(throwables.getMessage());
        }
    }

    public EmployeePayRollData addEmployeeToPayroll(String name, char gender, int salary, LocalDate date) {

        int employeeId = -1;
        Connection connection = null;
        EmployeePayRollData employeePayRollData = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }

        try(Statement statement = connection.createStatement()) {
            String sql = String.format("insert into employee ( name, salary, start, gender ) value ('%s', %s, '%s', '%s');", name, salary, date, gender );
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) employeeId = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }

        try(Statement statement = connection.createStatement()) {
            double deduction = salary * 0.2;
            double taxable_pay = salary - deduction;
            double tax = taxable_pay * 0.1;
            double netPay = salary - tax;

            String sql = String.format("insert into payroll_details (emp_id, basic_pay, deduction, taxable_pay, income_tax, net_pay) value ('%s', %s, '%s', '%s', '%s', '%s');", employeeId, salary, deduction, taxable_pay, tax, netPay);
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                employeePayRollData = new EmployeePayRollData(employeeId, name, salary, date);
            }
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }

        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return employeePayRollData;
    }
}
