package tasktracker.controller;

import tasktracker.model.Epic;
import tasktracker.model.Subtask;
import tasktracker.model.Task;

import java.util.List;

public interface TaskManager {

    int addTask(Task task);

    int addEpic(Epic epic);

    int addSubtask(Subtask subtask);

    void updateTask(int id, Task task);

    void deleteTask(int id);

    Task getTaskById(int id);
    Subtask getSubtaskById(int id);
    Epic getEpicById(int id);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    List<Subtask> getSubtasksByEpic(Epic epic);

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    List<Task> getHistory();
}