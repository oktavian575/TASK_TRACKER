package tasktracker.controller;

public class Managers {
    private Managers() {
        // Приватный конструктор, чтобы предотвратить создание экземпляра класса
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}