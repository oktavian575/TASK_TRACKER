package tasktracker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasktracker.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    public void setUp() {
        manager = Managers.getDefault();
    }

    @Test
    public void testAddAndGetTask() {
        int taskId = manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        Task task = manager.getTaskById(taskId);

        assertNotNull(task);
        assertEquals("Task1", task.getName());
        assertEquals("Description1", task.getDescription());
        assertEquals(TaskStatus.NEW, task.getStatus());
    }

    @Test
    public void testAddAndGetEpic() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);

        assertNotNull(epic);
        assertEquals("Epic1", epic.getName());
        assertEquals("Description1", epic.getDescription());
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void testAddAndGetSubtask() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);

        int subtaskId = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic));
        Subtask subtask = manager.getSubtaskById(subtaskId);

        assertNotNull(subtask);
        assertEquals("Subtask1", subtask.getName());
        assertEquals("Description1", subtask.getDescription());
        assertEquals(TaskStatus.NEW, subtask.getStatus());
        assertEquals(epic, subtask.getEpic());
    }

    @Test
    public void testDeleteTask() {
        int taskId = manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        manager.deleteTask(taskId);

        Task task = manager.getTaskById(taskId);
        assertNull(task);
    }

    @Test
    public void testDeleteEpic() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        int subtaskId = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, manager.getEpicById(epicId)));
        manager.deleteTask(epicId);

        Epic epic = manager.getEpicById(epicId);
        Subtask subtask = manager.getSubtaskById(subtaskId);
        assertNull(epic);
        assertNull(subtask);
    }

    @Test
    public void testDeleteSubtask() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        int subtaskId = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, manager.getEpicById(epicId)));
        manager.deleteTask(subtaskId);

        Subtask subtask = manager.getSubtaskById(subtaskId);
        assertNull(subtask);
    }

    @Test
    public void testUpdateTask() {
        int taskId = manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        Task task = manager.getTaskById(taskId);
        task.setStatus(TaskStatus.IN_PROGRESS);

        manager.updateTask(taskId, task);
        Task updatedTask = manager.getTaskById(taskId);

        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus());
    }

    @Test
    public void testUpdateEpic() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);
        epic.setStatus(TaskStatus.IN_PROGRESS);

        manager.updateTask(epicId, epic);
        Epic updatedEpic = manager.getEpicById(epicId);

        assertEquals(TaskStatus.IN_PROGRESS, updatedEpic.getStatus());
    }

    @Test
    public void testUpdateSubtask() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        int subtaskId = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, manager.getEpicById(epicId)));
        Subtask subtask = manager.getSubtaskById(subtaskId);
        subtask.setStatus(TaskStatus.IN_PROGRESS);

        manager.updateTask(subtaskId, subtask);
        Subtask updatedSubtask = manager.getSubtaskById(subtaskId);

        assertEquals(TaskStatus.IN_PROGRESS, updatedSubtask.getStatus());
    }

    @Test
    public void testGetAllTasks() {
        int taskId1 = manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        int taskId2 = manager.addTask(new Task("Task2", "Description2", TaskStatus.IN_PROGRESS));

        List<Task> tasks = manager.getTasks();
        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(task -> task.getId() == taskId1));
        assertTrue(tasks.stream().anyMatch(task -> task.getId() == taskId2));
    }

    @Test
    public void testGetAllEpics() {
        int epicId1 = manager.addEpic(new Epic("Epic1", "Description1"));
        int epicId2 = manager.addEpic(new Epic("Epic2", "Description2"));

        List<Epic> epics = manager.getEpics();
        assertEquals(2, epics.size());
        assertTrue(epics.stream().anyMatch(epic -> epic.getId() == epicId1));
        assertTrue(epics.stream().anyMatch(epic -> epic.getId() == epicId2));
    }

    @Test
    public void testGetAllSubtasks() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        int subtaskId1 = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, manager.getEpicById(epicId)));
        int subtaskId2 = manager.addSubtask(new Subtask("Subtask2", "Description2", TaskStatus.IN_PROGRESS, manager.getEpicById(epicId)));

        List<Subtask> subtasks = manager.getSubtasks();
        assertEquals(2, subtasks.size());
        assertTrue(subtasks.stream().anyMatch(subtask -> subtask.getId() == subtaskId1));
        assertTrue(subtasks.stream().anyMatch(subtask -> subtask.getId() == subtaskId2));
    }

    @Test
    public void testGetSubtasksByEpic() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);
        int subtaskId1 = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic));
        int subtaskId2 = manager.addSubtask(new Subtask("Subtask2", "Description2", TaskStatus.IN_PROGRESS, epic));

        List<Subtask> subtasks = manager.getSubtasksByEpic(epic);
        assertEquals(2, subtasks.size());
        assertTrue(subtasks.stream().anyMatch(subtask -> subtask.getId() == subtaskId1));
        assertTrue(subtasks.stream().anyMatch(subtask -> subtask.getId() == subtaskId2));
    }

    @Test
    public void testHistory() {
        int taskId1 = manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        int taskId2 = manager.addTask(new Task("Task2", "Description2", TaskStatus.IN_PROGRESS));
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        int subtaskId = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, manager.getEpicById(epicId)));

        manager.getTaskById(taskId1);
        manager.getTaskById(taskId2);
        manager.getTaskById(epicId);
        manager.getTaskById(subtaskId);

        List<Task> history = manager.getHistory();
        assertEquals(5, history.size());
        assertEquals(epicId, history.get(0).getId());
        assertEquals(taskId1, history.get(1).getId());
        assertEquals(taskId2, history.get(2).getId());
        assertEquals(epicId, history.get(3).getId());
        assertEquals(subtaskId, history.get(4).getId());
    }

    @Test
    public void testHistoryLimit() {
        for (int i = 0; i < 15; i++) {
            int taskId = manager.addTask(new Task("Task" + i, "Description" + i, TaskStatus.NEW));
            manager.getTaskById(taskId);
        }

        List<Task> history = manager.getHistory();
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
    @Test
    public void testTaskImmutability() {
        int taskId = manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        Task task = manager.getTaskById(taskId);
        Task originalTask = new Task("Task1", "Description1", TaskStatus.NEW);
        originalTask.setId(taskId);

        task.setStatus(TaskStatus.IN_PROGRESS);

        Task retrievedTask = manager.getTaskById(taskId);
        assertEquals(originalTask, retrievedTask);
    }

    @Test
    public void testEpicImmutability() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);
        Epic originalEpic = new Epic("Epic1", "Description1");
        originalEpic.setId(epicId);

        epic.setStatus(TaskStatus.IN_PROGRESS);

        Epic retrievedEpic = manager.getEpicById(epicId);
        assertEquals(originalEpic, retrievedEpic);
    }

    @Test
    public void testSubtaskImmutability() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);
        int subtaskId = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic));
        Subtask subtask = manager.getSubtaskById(subtaskId);
        Subtask originalSubtask = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic);
        originalSubtask.setId(subtaskId);

        subtask.setStatus(TaskStatus.IN_PROGRESS);

        Subtask retrievedSubtask = manager.getSubtaskById(subtaskId);
        assertEquals(originalSubtask, retrievedSubtask);
    }

    @Test
    public void testAddAndGetTaskWithGeneratedId() {
        int taskId = manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        Task task = manager.getTaskById(taskId);

        assertNotNull(task);
        assertEquals("Task1", task.getName());
        assertEquals("Description1", task.getDescription());
        assertEquals(TaskStatus.NEW, task.getStatus());
        assertTrue(task.getId() > 0);
    }

    @Test
    public void testAddAndGetEpicWithGeneratedId() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);

        assertNotNull(epic);
        assertEquals("Epic1", epic.getName());
        assertEquals("Description1", epic.getDescription());
        assertEquals(TaskStatus.NEW, epic.getStatus());
        assertTrue(epic.getId() > 0);
    }

    @Test
    public void testAddAndGetSubtaskWithGeneratedId() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);

        int subtaskId = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic));
        Subtask subtask = manager.getSubtaskById(subtaskId);

        assertNotNull(subtask);
        assertEquals("Subtask1", subtask.getName());
        assertEquals("Description1", subtask.getDescription());
        assertEquals(TaskStatus.NEW, subtask.getStatus());
        assertEquals(epic, subtask.getEpic());
        assertTrue(subtask.getId() > 0);
    }

    @Test
    public void testDeleteAllTasks() {
        manager.addTask(new Task("Task1", "Description1", TaskStatus.NEW));
        manager.addTask(new Task("Task2", "Description2", TaskStatus.IN_PROGRESS));

        manager.deleteTasks();

        List<Task> tasks = manager.getTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testDeleteAllSubtasks() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);
        manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic));
        manager.addSubtask(new Subtask("Subtask2", "Description2", TaskStatus.IN_PROGRESS, epic));

        manager.deleteSubtasks();

        List<Subtask> subtasks = manager.getSubtasks();
        assertTrue(subtasks.isEmpty());
    }

    @Test
    public void testDeleteAllEpics() {
        manager.addEpic(new Epic("Epic1", "Description1"));
        manager.addEpic(new Epic("Epic2", "Description2"));

        manager.deleteEpics();

        List<Epic> epics = manager.getEpics();
        assertTrue(epics.isEmpty());
    }

    @Test
    public void testEpicStatusUpdate() {
        int epicId = manager.addEpic(new Epic("Epic1", "Description1"));
        Epic epic = manager.getEpicById(epicId);

        assertEquals(TaskStatus.NEW, epic.getStatus());

        int subtaskId1 = manager.addSubtask(new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic));
        int subtaskId2 = manager.addSubtask(new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic));

        assertEquals(TaskStatus.NEW, epic.getStatus());

        Subtask subtask1 = manager.getSubtaskById(subtaskId1);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(subtaskId1, subtask1);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

        Subtask subtask2 = manager.getSubtaskById(subtaskId2);
        subtask2.setStatus(TaskStatus.DONE);
        manager.updateTask(subtaskId2, subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

        subtask1.setStatus(TaskStatus.DONE);
        manager.updateTask(subtaskId1, subtask1);

        assertEquals(TaskStatus.DONE, epic.getStatus());
    }
}