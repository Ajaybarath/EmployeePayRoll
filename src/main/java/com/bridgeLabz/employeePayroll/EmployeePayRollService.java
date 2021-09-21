package com.bridgeLabz.employeePayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRollService {

	
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
		String name= consoleInputReader.next();
		System.out.println("Enter Emp Salary : ");
		double salary = consoleInputReader.nextDouble();
		employeePayRollList.add(new EmployeePayRollData(id, name, salary));
				
	}
}
