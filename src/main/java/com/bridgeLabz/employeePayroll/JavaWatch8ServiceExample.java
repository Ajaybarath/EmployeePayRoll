package com.bridgeLabz.employeePayroll;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class JavaWatch8ServiceExample {

	private static final String ENTRY_CREATE = "";
	private static final String ENTRY_DELETE = "";
	private static final String ENTRY_MODIFY = "";
	private final WatchService watcher;
	private final Map<WatchKey, Path> dirWatchers;

	public JavaWatch8ServiceExample(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.dirWatchers = new HashMap<>();
		scanAndRegisterDirectories(dir);
	}

	private void registerDirMatchers(Path dir) {
//		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//
//		dirWatchers.put(key, dir);
	}

	private void scanAndRegisterDirectories(Path start) throws IOException {

		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

			public FileVisitResult preVisitorDirectory(Path dir, BasicFileAttributes atttrs) {
				registerDirMatchers(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	void processEvents() {
		while (true) {
			WatchKey key;
			try {

				key = watcher.take();

			} catch (Exception e) {
				return;
			}
			
			Path dir = dirWatchers.get(key);
			
			if (dir == null) continue;
			
			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				Path name = ((WatchEvent<Path>) event).context();
				Path child = dir.resolve(name);
				System.out.format("%s : %s\n", event.kind().name(), child);
				
				if (kind.equals(ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(child)) scanAndRegisterDirectories(child);
					} catch (Exception e) {
						
					}
				}
				else if(kind.equals(ENTRY_DELETE)) {
					if (Files.isDirectory(child)) dirWatchers.remove(key);
				}
			}
			
			boolean valid = key.reset();
			
			if (!valid) {
				dirWatchers.remove(key);
				if (dirWatchers.isEmpty()) break;
			}
			
		}
	}

}
