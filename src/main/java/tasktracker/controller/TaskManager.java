package tasktracker.controller;

import tasktracker.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private int idCounter;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        idCounter = 0;
    }

    public int generateNewId() {
        return ++idCounter;
    }

    public void addTask(Task task) {
        tasks.put(generateNewId(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(generateNewId(), epic);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(generateNewId(), subtask);
        subtask.getEpic().addSubtask(subtask);
    }

    public void updateTask(Task task) {
        if (task instanceof Epic)
            epics.put(task.getId(), (Epic) task);
        else if (task instanceof Subtask)
            subtasks.put(task.getId(), (Subtask) task);
        else
            tasks.put(task.getId(), task);
    }

    public void deleteTask(int id) {
        Task task = tasks.get(id);
        if (task instanceof Epic epic) {
            for (Subtask subtask : epic.getSubtasks())
                subtasks.remove(subtask.getId());
            epics.remove(id);
        } else if (task instanceof Subtask subtask) {
            Epic epic = subtask.getEpic();
            if (epic != null)
                epic.removeSubtask(subtask);

        } else tasks.remove(id);
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Subtask> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public Task getTaskByName(String name) {
        for (Task task : tasks.values())
            if (task.getName().equals(name))
                return task;
        return null;
    }

    public void printProject() {
        System.out.println("All Tasks:");
        for (Task task : getAllTasks())
            System.out.println(task.getId() + ": " + task.getName() + " - " + task.getStatus());

        System.out.println("\nAll Epics:");
        for (Epic epic : getAllEpics())
            System.out.println(epic.getId() + ": " + epic.getName() + " - " + epic.getStatus());

        System.out.println("\nAll Subtasks:");
        for (Subtask subtask : getAllSubtasks())
            System.out.println(subtask.getId() + ": " + subtask.getName() + " - " + subtask.getStatus());

        for (Epic epic : getAllEpics()) {
            System.out.println("\nSubtasks of Epic " + epic.getId() + ":");
            for (Subtask subtask : getSubtasksByEpic(epic))
                System.out.println(subtask.getId() + ": " + subtask.getName() + " - " + subtask.getStatus());
        }
    }
}