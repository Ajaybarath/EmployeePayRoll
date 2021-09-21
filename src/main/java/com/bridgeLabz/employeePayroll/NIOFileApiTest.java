package com.bridgeLabz.employeePayroll;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;


public class NIOFileApiTest {
	
	private static final String HOME = System.getProperty("user.home");
	private static final String PLAY_WITH_NIO = "TempFileTry";

	@Test
	public void givenPathWhenCheckedThenConfirm() throws IOException {
		
		Path homepath = Paths.get(HOME);
		
		Assert.assertTrue(Files.exists(homepath));
		
		Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		if (Files.exists(playPath)) Files.deleteIfExists(playPath);
		Assert.assertTrue(Files.notExists(playPath));
		
		IntStream.range(1, 10).forEach(cntr -> {
			Path tempFile = Paths.get(playPath + "/temp" + cntr);
			Assert.assertTrue(Files.notExists(tempFile));
			try {
				Files.createFile(tempFile);
			}
			catch (IOException e) {
				Assert.assertTrue(Files.exists(tempFile));
			}
		});
		
	}
	
	
	@Test
	public void givenADirectoryWhenWatchedListAllTheActivities() throws IOException {
		Path dir = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
		new JavaWatch8ServiceExample(dir).processEvents(); 
	}
	
	@Test
	public void writeDataAndGetEntriesCount() {
		EmployeePayRollData[] arrEmployeePayRollDatas = {
				new EmployeePayRollData(1,  "Ajay", 10000),
				new EmployeePayRollData(2,  "barath", 20000),
				new EmployeePayRollData(3,  "AjayB", 30000),
		};
		
		EmployeePayRollService employeePayRollService = new EmployeePayRollService(Arrays.asList(arrEmployeePayRollDatas));
		employeePayRollService.writeEmployeePayrollData(EmployeePayRollService.IOService.FILE_IO);
		long entries = employeePayRollService.countEntries();
		Assert.assertEquals(3, entries);
	
	}
	
	@Test
	public void writeDataAndGetEntriesCountAndPrintThem() {
		EmployeePayRollData[] arrEmployeePayRollDatas = {
				new EmployeePayRollData(1,  "Ajay", 10000),
				new EmployeePayRollData(2,  "barath", 20000),
				new EmployeePayRollData(3,  "AjayB", 30000),
		};
		
		EmployeePayRollService employeePayRollService = new EmployeePayRollService(Arrays.asList(arrEmployeePayRollDatas));
		employeePayRollService.writeEmployeePayrollData(EmployeePayRollService.IOService.FILE_IO);
		employeePayRollService.printEntries();
		long entries = employeePayRollService.countEntries();
		Assert.assertEquals(3, entries);
	
	}

}
