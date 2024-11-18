package tasktracker.model;

public class Subtask extends Task {
    private final Epic epic;

    public Subtask(int id, String name, String description, TaskStatus status, Epic epic) {
        super(id, name, description, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }
}