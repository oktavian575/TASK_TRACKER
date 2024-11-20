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
        return new ArrayList<>(history);
    }

    public void addToHistory(Task task) {
        if(task == null) return;
        history.addLast(task);
        if (history.size() > 10) {
            history.removeFirst();
        }
    }
}
