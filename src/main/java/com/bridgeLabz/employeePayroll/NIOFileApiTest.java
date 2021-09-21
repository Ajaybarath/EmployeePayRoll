package com.bridgeLabz.employeePayroll;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;


public class NIOFileApiTest {
	
	private static final String HOME = System.getProperty("user.home");
	private static final String PLAY_WITH_NIO = "TempFileTry";

	@Test
	public void givenPathWhenCheckedThenConfirm() {
		
		Path homepath = Paths.get(HOME);
		
		Assert.assertTrue(Files.exists(homepath));
		
		Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		if (Files.exists(playPath)) Files.deleteFiles(playPath.toFile());
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

}
