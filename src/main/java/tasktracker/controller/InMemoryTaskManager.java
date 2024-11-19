package tasktracker.controller;

import tasktracker.model.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private int idCounter;
    private final Deque<Integer> history;
    private final Map<Integer, TaskType> tasksType;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        idCounter = 0;
        history = new LinkedList<>();
        tasksType = new HashMap<>();
    }

    @Override
    public int generateNewId() {
        return ++idCounter;
    }

    @Override
    public int addTask(Task task) {
        idCounter = generateNewId();
        task.setId(idCounter);
        tasks.put(idCounter, task);
        tasksType.put(idCounter, TaskType.TASK_TYPE);
        return idCounter;
    }

    @Override
    public int addEpic(Epic epic) {
        idCounter = generateNewId();
        epic.setId(idCounter);
        epics.put(idCounter, epic);
        tasksType.put(idCounter, TaskType.EPIC_TASK_TYPE);
        return idCounter;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        idCounter = generateNewId();
        subtask.setId(idCounter);
        subtasks.put(idCounter, subtask);
        subtask.getEpic().addSubtask(subtask);
        tasksType.put(idCounter, TaskType.SUBTASK_TYPE);
        return idCounter;
    }

    @Override
    public void updateTask(int id, Task task) {
        if (task instanceof Epic)
            epics.put(id, (Epic) task);
        else if (task instanceof Subtask subtask) {
            subtasks.put(id, subtask);
            Epic epic = subtask.getEpic();
            epic.updateStatus();
        } else
            tasks.put(id, task);
    }

    @Override
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

    @Override
    public Task getTaskById(int id) {
        if (tasksType.get(id).equals(TaskType.TASK_TYPE)) {
            addToHistory(id);
            return tasks.get(id);
        } else if (tasksType.get(id).equals(TaskType.EPIC_TASK_TYPE)) {
            addToHistory(id);
            return epics.get(id);
        } else if (tasksType.get(id).equals(TaskType.SUBTASK_TYPE)) {
            addToHistory(id);
            return subtasks.get(id);
        } else return null;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            subtask.getEpic().removeSubtask(subtask);
            subtask.getEpic().updateStatus();
        }
        subtasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyTasks = new ArrayList<>();
        for (Integer id : history) {
            Task task = tasks.get(id);
            if (task != null) {
                historyTasks.add(task);
            } else {
                Epic epic = epics.get(id);
                if (epic != null) {
                    historyTasks.add(epic);
                } else {
                    Subtask subtask = subtasks.get(id);
                    if (subtask != null) {
                        historyTasks.add(subtask);
                    }
                }
            }
        }
        return historyTasks;
    }

    private void addToHistory(int id) {
        history.addLast(id);
        if (history.size() > 10) {
            history.removeFirst();
        }
    }
}