package tasktracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description, TaskStatus.NEW);
        this.subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();

    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateStatus();
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) {
            this.status = TaskStatus.NEW;
        }
        boolean allDone = true;
        boolean allNew = true;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (subtask.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
        }
        if (allDone) {
            this.status = TaskStatus.DONE;
        }
        if (allNew) {
            this.status = TaskStatus.NEW;
        }
        this.status = TaskStatus.IN_PROGRESS;
    }
}
