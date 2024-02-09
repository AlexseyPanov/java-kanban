package ru.yandex.app.service;

import ru.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// для хранения истории просмотров задач в памяти
public class InMemoryHistoryManager implements HistoryManager {
    // список просмотров
    Node history = null;
    // ссылки на элементы истории по id
    HashMap<Integer, Node> indexTask = new HashMap<>();

    @Override
    // добавить задачу
    public void add(Task task) {
        if (task == null) {
            return;
        }
        remove(task.getId());
        Node node = linkLast(task);
        indexTask.put(task.getId(), node);
    }

    @Override
    // Список просмотренных задач
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (indexTask.containsKey(id)) {
            Node node = indexTask.get(id);
            removeNode(node);
            indexTask.remove(id);
        }
    }

    /**
     * добавить запись в конец списка
     */
    private Node linkLast(Task task) {
        if (history == null) {
            history = new Node(task);
            history.setPrev(history);
            history.setNext(history);
            return history;
        } else {
            Node node = new Node(task);
            node.setNext(history);
            node.setPrev(history.getPrev());
            history.getPrev().setNext(node);
            history.setPrev(node);
            return node;
        }
    }
    /**
     * получить историю в виде списка задач
     */
    private List<Task> getTasks() {
        ArrayList<Task> res = new ArrayList<>();
        if (history != null) {
            Node node = history;
            do {
                res.add(node.getValue());
                node = node.getNext();
            } while (node != history);
        }
        return res;
    }
    /**
     * удалить узел задачи из списка
     */
    private void removeNode(Node node) {
        if (node == null) return;
        if (node == history) {  // это первый элемент
            // поправим ссылку на первый элемент списка
            history = (history.getNext() != history) ? history.getNext() : null;
        }
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        node.setPrev(null);
        node.setNext(null);
        node.setValue(null);
    }
}
