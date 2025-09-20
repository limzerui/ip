package kobe.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import kobe.task.Deadline;
import kobe.task.Event;
import kobe.task.Task;
import kobe.task.Todo;

/**
 * Provides file-based persistence for tasks.
 * Loads tasks from and saves tasks to a UTF-8 text file.
 */
public class Storage {
	private final Path filePath;

	/**
	 * Creates storage with the default path "data/kobe.txt".
	 */
	public Storage() {
		this(Paths.get("data", "kobe.txt"));
	}

	/**
	 * Creates storage using the specified path.
	 * @param filePath path to the data file
	 */
	public Storage(Path filePath) {
		this.filePath = filePath;
	}

	/**
	 * Loads tasks from the file if present.
	 * Missing files/directories are treated as an empty list.
	 * @return list of tasks parsed from the file
	 */
	public List<Task> load() {
		List<Task> tasks = new ArrayList<>();

		try {
			if (!Files.exists(filePath)) {
				// Ensure directory exists for first save later
				Path parent = filePath.getParent();
				if (parent != null && !Files.exists(parent)) {
					Files.createDirectories(parent);
				}
				return tasks; 
			}

			try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
				String line;
				while ((line = reader.readLine()) != null) {
					Task t = parseLine(line);
					if (t != null) {
						tasks.add(t);
					}
				}
			}
		} catch (IOException e) {
			return tasks;
		}

		return tasks;
	}

	/**
	 * Saves the given tasks to the file, creating parent directories if needed.
	 * @param tasks tasks to persist
	 */
	public void save(List<Task> tasks) {
		try {
			Path parent = filePath.getParent();
			if (parent != null && !Files.exists(parent)) {
				Files.createDirectories(parent);
			}

			try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
				for (Task task : tasks) {
					writer.write(task.toDataString());
					writer.newLine();
				}
			}
		} catch (IOException e) {
		}
	}

	private Task parseLine(String raw) {
		if (raw == null) {
			return null;
		}
		String line = raw.trim();
		if (line.isEmpty()) {
			return null;
		}

		String[] parts = line.split("\\|", -1); 
		// Expect trimmed parts like: ["T ", " 1 ", " read book"]
		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].trim();
		}

		if (parts.length < 3) {
			return null;
		}

		String type = parts[0];
		String doneFlag = parts[1];
		String description = parts[2];

		Task task;
		switch (type) {
			case "T":
				task = new Todo(description);
				break;
			case "D":
				if (parts.length < 4) return null;
				task = new Deadline(description, parts[3]);
				break;
			case "E":
				if (parts.length < 5) return null;
				task = new Event(description, parts[3], parts[4]);
				break;
			default:
				return null;
		}

		if ("1".equals(doneFlag)) {
			task.mark();
		}
		return task;
	}
}
