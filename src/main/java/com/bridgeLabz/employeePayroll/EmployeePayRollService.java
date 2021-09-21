package com.bridgeLabz.employeePayroll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.spi.IIOServiceProvider;

public class EmployeePayRollService {

	public enum IOService {
		CONSOLE_IO, FILE_IO
	}

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

	public void printEntries() {
		new EmployeePayrollFileIOService().printData();
	}

	public long countEntries() {
		return new EmployeePayrollFileIOService().countEnteries();
	}

}
