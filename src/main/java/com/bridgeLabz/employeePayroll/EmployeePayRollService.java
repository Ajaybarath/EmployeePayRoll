package com.bridgeLabz.employeePayroll;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.spi.IIOServiceProvider;

public class EmployeePayRollService {

	public EmployeePayRollService() {
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
	}

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO
	}

	EmployeePayrollDBService employeePayrollDBService;

	private List<EmployeePayRollData> employeePayRollList;

	public EmployeePayRollService(List<EmployeePayRollData> employeePayRollList) {
		this.employeePayRollList = employeePayRollList;

	}

	public static void main(String args[]) {

		ArrayList<EmployeePayRollData> employeePayRollList = new ArrayList<>();
		EmployeePayRollService employeePayRollService = new EmployeePayRollService(employeePayRollList);
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayRollService.readEmployeeData(consoleInputReader);
		employeePayRollService.writeEmployeePayrollData();

	}

	private void writeEmployeePayrollData() {

		System.out.println(employeePayRollList);
	}

	private void readEmployeeData(Scanner consoleInputReader) {
		System.out.println("Enter Emp id: ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter Emp name :");
		String name = consoleInputReader.next();
		System.out.println("Enter Emp Salary : ");
		double salary = consoleInputReader.nextDouble();
		employeePayRollList.add(new EmployeePayRollData(id, name, salary));

	}

	public void writeEmployeePayrollData(IOService ioservice) {
		if (ioservice.equals(IOService.CONSOLE_IO)) {
			System.out.println("Writing payroll roster to console" + employeePayRollList);

		} else if (ioservice.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().writeData(employeePayRollList);
		}
	}

	public void readEmployeePayrollData() throws IOException {

		List<String> list = new EmployeePayrollFileIOService().readData();
		
		System.out.println(list);

	}

	public void updateEmployeeData(String name, int salary) throws EmployeePayrollException {
		int result = employeePayrollDBService.updateEmployeeData(name, salary);
		if (result == 0) {
			return;
		}

		EmployeePayRollData employeePayRollData = this.getEmployeePayrollData(name);
		if (employeePayRollData != null) {
			employeePayRollData.setSalary(salary);
		}


	}

	public EmployeePayRollData getEmployeePayrollData(String name) {
		EmployeePayRollData employeePayRollData = this.employeePayRollList.stream().filter(employeePayRollData1 -> employeePayRollData1.getName().equals(name))
				.findFirst()
				.orElse(null);

		return employeePayRollData;

	}

	public List<EmployeePayRollData> readEmployeePayrollData(IOService service) throws SQLException {

		if (service.equals(IOService.DB_IO)) {
			this.employeePayRollList = employeePayrollDBService.readData();
		}
		return employeePayRollList;
	}

	public List<EmployeePayRollData> getEmployeePayrollDataByName(String name) throws EmployeePayrollException {
		return employeePayrollDBService.getEmployeePayrollData(name);
	}

	public List<EmployeePayRollData> getEmployeePayrollDataByDateRange(String startDate, String endDate) throws EmployeePayrollException {
		return employeePayrollDBService.getEmployeePayrollDataByDateRange(startDate, endDate);
	}


	public List<EmployeePayRollData> getSumOfEmployeeSalary() throws EmployeePayrollException {
		return employeePayrollDBService.getSumOfEmployeeSalary();
	}


	public List<EmployeePayRollData> getAvgSumMinMaxEmployeeSalary(String data) throws EmployeePayrollException {
		return employeePayrollDBService.getAvgSumMinMaxEmployeeSalary(data);

	}
	public List<EmployeePayRollData> getEmployeeCountByGender() throws EmployeePayrollException {
		return employeePayrollDBService.getEmployeeCountByGender();

	}

	public EmployeePayRollData addEmployeeToPayroll(String name, char gender, int salary, LocalDate date) {
		return employeePayrollDBService.addEmployeeToPayroll(name, gender, salary, date);
	}

	public EmployeeDepartment addEmployeeDepartment(String department, String name) throws EmployeePayrollException {
		return employeePayrollDBService.addDepartmentToEmployee(department, name);
	}

	public void printList() {
		for (EmployeePayRollData data : employeePayRollList){
			System.out.println(data.getName());
		}
	}

	public int listSize() {
		return employeePayRollList.size();
	}

	public void printEntries() {
		new EmployeePayrollFileIOService().printData();
	}

	public long countEntries() {
		return new EmployeePayrollFileIOService().countEnteries();
	}

}
