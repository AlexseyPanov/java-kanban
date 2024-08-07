package ru.yandex.app.model;

import java.util.Objects;

import static ru.yandex.app.model.TaskStatus.NEW;

public class Task {

    protected Integer id = 0;//идентификатор задачи
    protected String name;//название задачи
    protected String description;//описание задачи
    protected TaskStatus taskStatus = NEW;
    protected TaskType taskType;

    //конструкторы
    public Task() {
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    public Task(String name, TaskType taskType, TaskStatus status, String description) {
        this.taskType = taskType;
        this.name = name;
        this.description = description;
        this.taskStatus = status;
    }

    //методы get и set
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

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(id, task.id) &&
                Objects.equals(taskStatus, task.taskStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, taskStatus);
    }

    @Override
    public String toString() {
        return "Задача{" +
                "название='" + name + '\'' +
                ", описание='" + description + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + taskStatus + '\'';
    }

    public String getDescriptionTask(){
        return getId() + "," + TaskType.TASK + "," + getName() + "," + getTaskStatus() + ","
                + getDescription();
    }
}
