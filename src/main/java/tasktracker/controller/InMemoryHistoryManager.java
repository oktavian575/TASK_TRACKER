package tasktracker.controller;

import tasktracker.model.Epic;
import tasktracker.model.Subtask;
import tasktracker.model.Task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    final Deque<Task> history;

    public InMemoryHistoryManager() {
        this.history = new ArrayDeque<>();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyTasks = new ArrayList<>();
        for (Task task : history) {
            if (task != null)
                historyTasks.add(task);
        }
        return historyTasks;
    }

    public void addToHistory(Task task) {
        history.addLast(task);
        if (history.size() > 10) {
            history.removeFirst();
        }
    }
}
