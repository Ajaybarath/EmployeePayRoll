package com.bridgeLabz.employeePayroll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollFileIOService {

	public void writeData(List<EmployeePayRollData> employeePayRollList) {

		StringBuffer empBuffer = new StringBuffer();
		employeePayRollList.forEach(employee -> {
			String empDataString = employee.toString().concat("\n");
			empBuffer.append(empDataString);
		});

		Path path = Paths.get("payroll-file.txt");
		byte[] strToBytes = empBuffer.toString().getBytes();

		try {
			Files.write(path, strToBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> readData() throws IOException {

		List<String> employeePayRollList = new ArrayList<>();
		
		Files.lines(new File("payroll-file.txt").toPath()).forEach(employee -> {
			employeePayRollList.add(employee);
		});
		
		return employeePayRollList;
	}

	
	public void printData() {

		try {
			Files.lines(new File("payroll-file.txt").toPath()).forEach(System.out::println);
		} catch (IOException e) {
		}
	}

	public long countEnteries() {
		long entries = 0;

		try {
			entries = Files.lines(new File("payroll-file.txt").toPath()).count();
		} catch (IOException e) {
		}
		return entries;
	}
}
