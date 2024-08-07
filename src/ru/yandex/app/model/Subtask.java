package ru.yandex.app.model;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask() {
    }

    public Subtask(String name, TaskType taskType, TaskStatus status, String description,
                   int epicID) {
        super(name, taskType, status, description);
        this.epicId = epicID;
    }

    public Subtask(String name, String description) {
        super(name, description);
    }

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUB_TASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return getEpicId() == subtask.getEpicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, taskStatus, epicId);
    }

    @Override
    public String toString() {
        return "Подзадача (Subtask) {" +
                "название='" + name + '\'' +
                ", описание='" + description + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + taskStatus + '\'' +
                ", id эпика='" + epicId + '}' + '\'';
    }
}