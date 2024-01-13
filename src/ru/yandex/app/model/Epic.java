package ru.yandex.app.model;

import ru.yandex.app.service.TaskStatus;
import ru.yandex.app.service.TaskType;

public class Epic extends Task {

    public Epic(int id, String name, String description, TaskStatus taskStatus, TaskType taskType) {
        super(id, name, description, taskStatus, taskType);
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "ru.yandex.app.model.Epic{id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskType=" + taskType +
                '}';
    }
}
