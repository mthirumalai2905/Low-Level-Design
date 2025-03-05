import java.util.*;

class TaskSystem {
    private static class Task {
        private static int idCounter = 1;
        private int id;
        private String title;
        private boolean completed;

        public Task(String title) {
            this.id = idCounter++;
            this.title = title;
            this.completed = false;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public boolean isCompleted() { return completed; }
        public void markComplete() { this.completed = true; }
        public void markIncomplete() { this.completed = false; }

        @Override
        public String toString() {
            return id + ". " + title + " [" + (completed ? "Done" : "Pending") + "]";
        }
    }

    private List<Task> tasks = new ArrayList<>();

    public void addTask(String title) {
        tasks.add(new Task(title));
    }

    public void completeTask(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                task.markComplete();
                return;
            }
        }
    }

    public void removeTask(int taskId) {
        tasks.removeIf(task -> task.getId() == taskId);
    }

    public void showTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public static void main(String[] args) {
        TaskSystem taskSystem = new TaskSystem();
        taskSystem.addTask("Buy groceries");
        taskSystem.addTask("Read a book");
        taskSystem.addTask("Exercise");

        System.out.println("Tasks:");
        taskSystem.showTasks();

        taskSystem.completeTask(2);
        System.out.println("\nAfter completing Task 2:");
        taskSystem.showTasks();

        taskSystem.removeTask(1);
        System.out.println("\nAfter removing Task 1:");
        taskSystem.showTasks();
    }
}
