package ru.yandex.app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTest {
    private final File testFile = new File("resources/test/tasksTest.csv");
    private final File baseFile = new File("resources/test/tasksEmpty.csv");
    private Path src = Path.of("resources/test/tasksTemplate.csv");
    private Path dst = Path.of("resources/test/tasksTest.csv");
    private FileBackedTaskManager fileBackedTasksManager;

    public FileBackedTaskManagerTest() {
    }

    @Test
    void loadFromFile() {
        FileBackedTaskManager mgr = FileBackedTaskManager.loadFromFile(this.testFile);
        Assertions.assertNotNull(mgr);
    }
    void restoreTest() {
        try {
            Files.copy(src, dst, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void historyToString() {
        restoreTest();
        FileBackedTaskManager testManager = new FileBackedTaskManager(testFile);
        HistoryManager historyManager = testManager.getHistoryManager();
        String pattern = "5,3,1,2,4";
        String history = FileBackedTaskManager.historyToString(historyManager);
        System.out.println("his="+history);
        assertEquals(pattern, history);
    }

    @Test
    void historyFromString() {
        String pattern = "5,3,1,2,4";
        List<Integer> history = FileBackedTaskManager.historyFromString(pattern);
        assertEquals(List.of(5, 3, 1, 2, 4), history);
    }
}
