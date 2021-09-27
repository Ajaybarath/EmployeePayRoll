package com.bridgeLabz.employeePayroll;

import java.time.LocalDate;

public class EmployeePayRollData {

	private LocalDate date;
	private int id;
	private String name;
	private double salary;
	private String gender;
	
	
	public EmployeePayRollData(int id, String name, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public EmployeePayRollData(int id, String name, double salary, LocalDate date) {
		this(id, name, salary);
		this.date = date;
	}

    public EmployeePayRollData(double salary, String gender) {
		this.gender = gender;
		this.salary = salary;
    }

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	

}
