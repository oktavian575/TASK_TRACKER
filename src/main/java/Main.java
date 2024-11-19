import tasktracker.controller.Managers;
import tasktracker.controller.TaskManager;
import tasktracker.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        List<Integer> tasksIds = new ArrayList<>();
        List<Integer> epicsIds = new ArrayList<>();
        List<Integer> subtaskIds = new ArrayList<>();

        tasksIds.add(manager.addTask(new Task("Moving", "Move to a new apartment", TaskStatus.NEW)));
        tasksIds.add(manager.addTask(new Task("Java", "Complete task tracker", TaskStatus.IN_PROGRESS)));

        epicsIds.add(manager.addEpic(new Epic("Project A", "Develop a new project")));
        epicsIds.add(manager.addEpic(new Epic("Project B", "Develop a new project")));

        subtaskIds.add(manager.addSubtask(new Subtask("Design", "Create project design", TaskStatus.NEW, manager.getEpics().getFirst())));
        subtaskIds.add(manager.addSubtask(new Subtask("Design", "Create project design", TaskStatus.NEW, manager.getEpics().getFirst())));
        subtaskIds.add(manager.addSubtask(new Subtask("Implementation", "Implement project features", TaskStatus.NEW, manager.getEpics().getLast())));

        printAllTasks(manager);

        // Update tasks with a new status
        Task updateTask = manager.getTaskById(tasksIds.getFirst());
        updateTask = manager.getTaskById(tasksIds.getFirst());

        Task updateSubTask = manager.getSubtasks().getFirst();
        manager.updateTask(updateTask.getId(), new Task(updateTask.getName(), updateTask.getDescription(), TaskStatus.IN_PROGRESS));
        manager.updateTask(updateSubTask.getId(), new Subtask(updateSubTask.getName(), updateSubTask.getDescription(), TaskStatus.IN_PROGRESS, manager.getEpics().getLast()));

        System.out.println("\nAll Tasks after updating:");
        printAllTasks(manager);

        manager.deleteTask(updateTask.getId());
        manager.deleteTask(updateSubTask.getId());
        System.out.println("\nAll Subtasks after deleting Subtask:");
        printAllTasks(manager);

        // Test history
        System.out.println("\nHistory of viewed tasks:");
        if (manager.getHistory().isEmpty())
            System.out.println("No history");
        else
            for (Task task : manager.getHistory()) {
                System.out.println(task.getId() + ": " + task.getName() + " - " + task.getStatus());
            }

        // Additional history tests
        manager.getTaskById(tasksIds.getLast());
        manager.getTaskById(epicsIds.getFirst());
        manager.getTaskById(subtaskIds.getLast());
        manager.getTaskById(subtaskIds.getFirst());

        System.out.println("\nUpdated History of viewed tasks:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
        manager.deleteEpics();
        manager.deleteSubtasks();
        manager.deleteTasks();

    }

    public static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubtasksByEpic((epic))) {
                System.out.println("--> " + task);
            }

        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

}