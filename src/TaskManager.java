import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public int id = 0;
    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    public TaskManager() {
        id++;
        this.id = id;
        this.taskMap = taskMap;
        this.epicMap = epicMap;
        this.subtaskMap = subtaskMap;
    }

    public void addTask(String name, String description, TaskStatus status) {
        Task task = new Task(id, name, description, status, TaskType.TASK);
        taskMap.put(task.id, task);
    }

    public void addSubtask(String name, String description, TaskStatus status) {
        Subtask subtask = new Subtask(id, name, description, status, TaskType.SUB_TASK);
        subtaskMap.put(subtask.id, subtask);
    }

    public void addEpic(String name, String description, TaskStatus status) {
        Epic epic = new Epic(id, name, description, status, TaskType.EPIC);
        epicMap.put(epic.id, epic);
    }
    public Task getTaskId(int id) {
        return taskMap.get(id);
    }
    public void removeTask(int id) {
        taskMap.remove(id);
    }
    public void removeAllTask() {
        taskMap.clear();
    }
    public HashMap<Integer, Task> PrintTaskMap() {
        return taskMap;
    }
    public HashMap<Integer, Epic> PrintEpicMap() {
        return epicMap;
    }
    public HashMap<Integer, Subtask> PrintSubtaskMap() {
        return subtaskMap;
    }
}
