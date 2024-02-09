package ru.yandex.app.service;

import ru.yandex.app.model.Task;

import java.util.List;

public interface HistoryManager {
    // добавить задачу в историю
    void add(Task task);
    // просмотренные задачи
    List<Task> getHistory();
    // удалить задачу по id
    void remove(int id);
}
