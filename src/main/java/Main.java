import tasktracker.controller.TaskManager;
import tasktracker.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        List<Integer> tasksIds = new ArrayList<>();
        List<Integer> epicsIds = new ArrayList<>();
        List<Integer> subtaskIds = new ArrayList<>();

        tasksIds.add(manager.addTask(new Task("Moving", "Move to a new apartment", TaskStatus.NEW)));
        tasksIds.add(manager.addTask(new Task("Java", "Complete task tracker", TaskStatus.IN_PROGRESS)));

        epicsIds.add(manager.addEpic(new Epic("Project A", "Develop a new project")));
        epicsIds.add(manager.addEpic(new Epic("Project B", "Develop a new project")));

        subtaskIds.add(manager.addSubtask(new Subtask("Design", "Create project design", TaskStatus.NEW, manager.getAllEpics().getFirst())));
        subtaskIds.add(manager.addSubtask(new Subtask("Design", "Create project design", TaskStatus.NEW, manager.getAllEpics().getFirst())));
        subtaskIds.add(manager.addSubtask(new Subtask("Implementation", "Implement project features", TaskStatus.NEW, manager.getAllEpics().getLast())));

        printProject(manager);

        // Update tasks with a new status
        Task updateTask = manager.getTaskById(tasksIds.getFirst());
        Task updateSubTask = manager.getAllSubtasks().getFirst();
        manager.updateTask(updateTask.getId(), new Task(updateTask.getName(), updateTask.getDescription(), TaskStatus.IN_PROGRESS));
        manager.updateTask(updateSubTask.getId(), new Subtask(updateSubTask.getName(), updateSubTask.getDescription(), TaskStatus.IN_PROGRESS, manager.getAllEpics().getLast()));

        System.out.println("\nAll Tasks after updating:");
        printProject(manager);

        manager.deleteTask(updateTask.getId());
        manager.deleteTask(updateSubTask.getId());
        System.out.println("\nAll Subtasks after deleting Subtask:");
        printProject(manager);

    }

    public static void printProject(TaskManager manager) {
        System.out.println("All Tasks:");
        for (Task task : manager.getAllTasks())
            System.out.println(task.getId() + ": " + task.getName() + " - " + task.getStatus());

        System.out.println("\nAll Epics:");
        for (Epic epic : manager.getAllEpics())
            System.out.println(epic.getId() + ": " + epic.getName() + " - " + epic.getStatus());

        System.out.println("\nAll Subtasks:");
        for (Subtask subtask : manager.getAllSubtasks())
            System.out.println(subtask.getId() + ": " + subtask.getName() + " - " + subtask.getStatus());

        for (Epic epic : manager.getAllEpics()) {
            System.out.println("\nSubtasks of Epic " + epic.getId() + ":");
            for (Subtask subtask : manager.getSubtasksByEpic(epic))
                System.out.println(subtask.getId() + ": " + subtask.getName() + " - " + subtask.getStatus());
        }
    }
}