package tasktracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    public void setUp() {
        epic = new Epic("Epic1", "Description1");
        epic.setId(1);
        subtask = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic);
        subtask.setId(1);
    }

    @Test
    public void testEqualityById() {
        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic);
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.IN_PROGRESS, epic);
        subtask1.setId(1);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2);
    }

    @Test
    public void testInequalityById() {
        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic);
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.IN_PROGRESS, epic);
        subtask1.setId(1);
        subtask2.setId(2);

        assertNotEquals(subtask1, subtask2);
    }


    @Test
    public void testImmutability() {
        Subtask originalSubtask = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic);
        originalSubtask.setId(1);

        subtask.setStatus(TaskStatus.IN_PROGRESS);

        assertEquals(originalSubtask, subtask);
    }
}
