package ru.yandex.app.service;

import ru.yandex.app.model.Task;

import java.util.LinkedList;
import java.util.List;

// для хранения истории просмотров задач в памяти
public class InMemoryHistoryManager implements HistoryManager {
    // список просмотров
    private LinkedList<Task> history = new LinkedList<>();
    // Лимит задач должен возвращать последние 10 просмотренных задач
    private final int LIMIT_TASKS = 10;

    @Override
    // добавить задачу
    //TODO В следующем спринте вам нужно будет убрать дубли и расширить её размер
    public void add(Task task) {
        if (task == null) {
            return;
        }
        //просмотренные задачи должны добавляться в конец.
        if (history.size() >= LIMIT_TASKS) {
            history.removeFirst();//Удаляет и возвращает первый элемент из этого списка.
        }
        history.add(task);
    }

    @Override
    // Список последних 10 просмотренных задач
    public List<Task> getHistory() {
        return history;
    }
}
