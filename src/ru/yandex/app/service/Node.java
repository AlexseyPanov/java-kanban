package ru.yandex.app.service;

import ru.yandex.app.model.Task;

public class Node {

    private Task value;
    private Node prevTask;
    private Node nextTask;

    /**
     * конструкторы
     */
    public Node() {
    }

    public Node(Task value) {
        this.value = value;
    }

    /**
     * get и set
     */
    public Task getValue() {
        return value;
    }

    public void setValue(Task value) {
        this.value = value;
    }

    public Node getPrev() {
        return prevTask;
    }

    public void setPrev(Node prevTask) {
        this.prevTask = prevTask;
    }

    public Node getNext() {
        return nextTask;
    }

    public void setNext(Node nextTask) {
        this.nextTask = nextTask;
    }
}
