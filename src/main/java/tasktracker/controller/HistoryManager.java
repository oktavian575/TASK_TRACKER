package tasktracker.controller;
import tasktracker.model.*;

import java.util.Deque;
import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void addToHistory(Task task);
}
