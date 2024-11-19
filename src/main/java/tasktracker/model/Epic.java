package tasktracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        setType(TaskType.EPIC_TASK_TYPE);
        this.subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    //Обновлением статуса эпика занимается Эпик
    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();
    }

    //Обновлением статуса эпика занимается Эпик
    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateStatus();
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) {
            setStatus(TaskStatus.NEW);
        }
        setStatus(TaskStatus.IN_PROGRESS);
        boolean allDone = true;
        boolean allNew = true;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.DONE) allDone = false;
            else if (subtask.getStatus() != TaskStatus.NEW) allNew = false;
        }
        if (allDone) setStatus(TaskStatus.DONE);
        else if (allNew) setStatus(TaskStatus.NEW);
    }

}