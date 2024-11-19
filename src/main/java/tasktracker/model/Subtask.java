package tasktracker.model;

public class Subtask extends Task {
    private final Epic epic;

    public Subtask(String name, String description, TaskStatus status, Epic epic) {
        super(name, description, status);
        setType(TaskType.SUBTASK_TYPE);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }
}