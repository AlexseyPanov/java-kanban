package ru.yandex.app.model;

import ru.yandex.app.service.TaskStatus;
import ru.yandex.app.service.TaskType;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, List<Integer> subtaskIds) {
        super(name, description);
        this.subtaskIds = subtaskIds;
    }

    public Epic(int id, String name, String description, TaskStatus taskStatus, TaskType taskType) {
        super(name, description);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
