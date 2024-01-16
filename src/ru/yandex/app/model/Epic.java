package ru.yandex.app.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic() {
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, List<Integer> subtaskIds) {
        super(name, description, TaskStatus.NEW);
        this.subtaskIds = subtaskIds;
    }

    public List<Integer> getSubtaskIds() {
        if (subtaskIds == null) {
            subtaskIds = new ArrayList<>();
        }
        return subtaskIds;
    }

    public void setSubtaskIds(List<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
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
