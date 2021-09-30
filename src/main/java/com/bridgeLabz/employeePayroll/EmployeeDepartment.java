package com.bridgeLabz.employeePayroll;

public class EmployeeDepartment {

    int emp_id;

    int department_id;

    public EmployeeDepartment() {

    }

    public EmployeeDepartment(int emp_id, int department_id) {
        this.emp_id = emp_id;
        this.department_id = department_id;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }
}
