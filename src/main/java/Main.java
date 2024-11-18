import tasktracker.controller.TaskManager;
import tasktracker.model.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task(manager.generateNewId(), "Moving", "Move to a new apartment", TaskStatus.NEW));
        manager.addTask(new Task(manager.generateNewId(), "Java", "Complete task tracker", TaskStatus.IN_PROGRESS));
        manager.addEpic(new Epic(manager.generateNewId(), "Project A", "Develop a new project"));
        manager.addEpic(new Epic(manager.generateNewId(), "Project B", "Develop a new project"));
        manager.addSubtask(new Subtask(manager.generateNewId(), "Design", "Create project design", TaskStatus.NEW, manager.getAllEpics().getFirst()));
        manager.addSubtask(new Subtask(manager.generateNewId(), "Implementation", "Implement project features", TaskStatus.NEW, manager.getAllEpics().getFirst()));
        manager.addSubtask(new Subtask(manager.generateNewId(), "Implementation", "Implement project features", TaskStatus.NEW, manager.getAllEpics().getLast()));
        manager.printProject();

        // Update tasks with a new status
        Task updateTask = manager.getTaskById(1);
        Task updateSubTask = manager.getAllSubtasks().getFirst();
        manager.updateTask(new Task(updateTask.getId(), updateTask.getName(), updateTask.getDescription(), TaskStatus.IN_PROGRESS));
        manager.updateTask(new Subtask(updateSubTask.getId(), updateSubTask.getName(), updateSubTask.getDescription(), TaskStatus.IN_PROGRESS, manager.getAllEpics().getLast()));

        System.out.println("\nAll Tasks after updating:");
        manager.printProject();

        manager.deleteTask(updateTask.getId());
        manager.deleteTask(updateSubTask.getId());
        System.out.println("\nAll Subtasks after deleting Subtask:");
        manager.printProject();
    }

}