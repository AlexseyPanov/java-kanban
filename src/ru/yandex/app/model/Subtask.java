package ru.yandex.app.model;

import ru.yandex.app.service.TaskStatus;
import ru.yandex.app.service.TaskType;

public class Subtask extends Task {
    public int epicId;

    public Subtask(int id, String name, String description, TaskStatus taskStatus, TaskType taskType, int epicId) {
        super(id, name, description, taskStatus, taskType);
        this.epicId = epicId;
        this.taskType = taskType;
    }

    public Subtask(int id, String name, String description, TaskStatus taskStatus, TaskType taskType) {
        super(id, name, description, taskStatus, taskType);
        this.taskType = taskType;
    }

    public Subtask(int id, String name, String description, TaskStatus status, TaskType taskType) {
        super();
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "ru.yandex.app.model.Subtask{" +
                "epicId=" + epicId + ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskType=" + taskType +
                '}';
    }
}