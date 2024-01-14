package ru.yandex.app.model;

import ru.yandex.app.service.TaskStatus;
import ru.yandex.app.service.TaskType;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int epicId, String name, String description) {
        super(name, description);
        this.epicId = epicId;
    }



    public Subtask(int id, String name, String description, TaskStatus taskStatus, int epicId) {
        super(id, name, description, taskStatus);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
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