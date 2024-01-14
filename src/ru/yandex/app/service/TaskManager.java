package ru.yandex.app.service;

import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public int id = 1;
    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, Subtask> subtaskMap = new HashMap<>();


    public void addTask(String name, String description, TaskStatus taskStatus) {
        Task task = new Task(id, name, description, taskStatus);
        task.getTaskType();
        taskMap.put(id, task);
        id++;
    }

    public void addEpic(String name, String description, TaskStatus taskStatus
            , String nameSub, String descriptionSub, TaskStatus statusSub) {
        Epic epic = new Epic(name, description);
        epic.getTaskType();
        epicMap.put(id, epic);
        id++;

        Subtask subtask = new Subtask(id, nameSub, descriptionSub, statusSub, id - 1);
        subtask.getTaskType();
        subtaskMap.put(id, subtask);
        id++;
    }

    public void addSubTask(String name, String description, TaskStatus taskStatus, int parentsEpicId) {
        Subtask newSubtask = new Subtask(id, name, description, taskStatus, parentsEpicId);
        newSubtask.getTaskType();
        id++;
        subtaskMap.put(id, newSubtask);
    }

    public Task getTask(Integer id) {
        return taskMap.get(id);
    }

    public Subtask getSubtask(Integer id) {
        return subtaskMap.get(id);
    }

    public Epic getEpic(Integer id) {
        return epicMap.get(id);
    }

    public void removeTask(Integer id) {
        taskMap.remove(id);
    }

    public void removeSubtask(Integer id) {
        subtaskMap.remove(id);
    }

    public void removeEpic(Integer id) {
        epicMap.remove(id);
    }

    public void removeAllSubtask() {
        subtaskMap.clear();
    }

    public void removeAllEpic() {
        epicMap.clear();
    }

    public Task getTaskId(Integer id) {
        return taskMap.get(id);
    }

    public void removeAllTask() {
        taskMap.clear();
    }

    public HashMap<Integer, Task> PrintTaskMap() {
        return taskMap;
    }

    public HashMap<Integer, Epic> printEpicMap() {
        return epicMap;
    }

    public HashMap<Integer, Subtask> printSubtaskMap() {
        return subtaskMap;
    }


}
