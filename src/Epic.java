public class Epic extends Task{

    public Epic(int id, String name, String description, TaskStatus taskStatus, TaskType taskType) {
        super(id, name, description, taskStatus, taskType);
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "Epic{id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskType=" + taskType +
                '}';
    }
}
