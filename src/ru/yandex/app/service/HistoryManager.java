package ru.yandex.app.service;

import ru.yandex.app.model.Task;

import java.util.List;

public interface HistoryManager {
    // добавить задачу в историю
    void add(Task task);
    // последние 10 просмотренных задач
    List<Task> getHistory();
}
