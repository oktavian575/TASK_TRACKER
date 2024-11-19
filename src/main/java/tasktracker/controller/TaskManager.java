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

    public int addTask(Task task) {
        idCounter = generateNewId();
        task.setId(idCounter);
        tasks.put(idCounter, task);
        return idCounter;
    }

    public int addEpic(Epic epic) {
        idCounter = generateNewId();
        epic.setId(idCounter);
        epics.put(idCounter, epic);
        return idCounter;
    }

    public int addSubtask(Subtask subtask) {
        idCounter = generateNewId();
        subtask.setId(idCounter);
        subtasks.put(idCounter, subtask);
        subtask.getEpic().addSubtask(subtask);
        return idCounter;
    }

    public void updateTask(int id, Task task) {
        if (task instanceof Epic)
            epics.put(id, (Epic) task);
        else if (task instanceof Subtask){
            Subtask subtask = (Subtask) task;
            subtasks.put(id, subtask);
            Epic epic = subtask.getEpic();
            epic.updateStatus();
        }
        else
            tasks.put(id, task);
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

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            subtask.getEpic().removeSubtask(subtask);
            subtask.getEpic().updateStatus();
        }
        subtasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }
}