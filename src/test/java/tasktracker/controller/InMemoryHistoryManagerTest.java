package tasktracker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasktracker.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddAndGetHistory() {
        Task task1 = new Task("Task1", "Description1", TaskStatus.NEW);
        Task task2 = new Task("Task2", "Description2", TaskStatus.IN_PROGRESS);
        Epic epic = new Epic("Epic1", "Description1");
        Subtask subtask = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic);

        historyManager.addToHistory(task1);
        historyManager.addToHistory(task2);
        historyManager.addToHistory(epic);
        historyManager.addToHistory(subtask);

        List<Task> history = historyManager.getHistory();
        assertEquals(4, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(epic, history.get(2));
        assertEquals(subtask, history.get(3));
    }

    @Test
    public void testHistoryLimit() {
        for (int i = 0; i < 15; i++) {
            Task task = new Task("Task" + i, "Description" + i, TaskStatus.NEW);
            historyManager.addToHistory(task);
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size());
        assertEquals("Task5", history.get(0).getName());
        assertEquals("Task6", history.get(1).getName());
        assertEquals("Task7", history.get(2).getName());
        assertEquals("Task8", history.get(3).getName());
        assertEquals("Task9", history.get(4).getName());
        assertEquals("Task10", history.get(5).getName());
        assertEquals("Task11", history.get(6).getName());
        assertEquals("Task12", history.get(7).getName());
        assertEquals("Task13", history.get(8).getName());
        assertEquals("Task14", history.get(9).getName());
    }
}
