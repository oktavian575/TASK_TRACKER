package tasktracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void testEqualityById() {
        Task task1 = new Task("Task1", "Description1", TaskStatus.NEW);
        Task task2 = new Task("Task2", "Description2", TaskStatus.IN_PROGRESS);
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2);
    }

    @Test
    public void testInequalityById() {
        Task task1 = new Task("Task1", "Description1", TaskStatus.NEW);
        Task task2 = new Task("Task2", "Description2", TaskStatus.IN_PROGRESS);
        task1.setId(1);
        task2.setId(2);

        assertNotEquals(task1, task2);
    }

    @Test
    public void testImmutability() {
        Task task = new Task("Task1", "Description1", TaskStatus.NEW);
        Task originalTask = new Task("Task1", "Description1", TaskStatus.NEW);
        originalTask.setId(task.getId());

        task.setStatus(TaskStatus.IN_PROGRESS);

        assertEquals(originalTask, task);
    }
}
