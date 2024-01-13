import java.util.Objects;

public class Task {
    protected int id = 0;
    protected String name;
    protected String description;

    protected TaskStatus taskStatus;
    protected TaskType taskType;

    public Task(int id, String name, String description, TaskStatus taskStatus, TaskType taskType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taskStatus = taskStatus;
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && taskStatus.equals(task.taskStatus) && taskType.equals(task.taskType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskStatus, taskType, name, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", taskStatus=" + taskStatus + '\'' +
                ", taskType=" + taskType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
