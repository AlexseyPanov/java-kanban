package ru.yandex.app.service;

import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    // получить новый ID
    private int getNextId() {
        return id++;
    }

    //добавим задачу
    public int addTask(Task task) {
        if (task == null) {
            return -1;
        }
        int id = getNextId();
        task.setId(id);
        task.getTaskType();
        taskMap.put(id, task);
        return id;
    }

    //добавим эпик
    public int addEpic(Epic epic) {
        if (epic == null) {
            return -1;
        }
        int id = getNextId();
        epic.setId(id);
        epic.getTaskType();
        epicMap.put(id, epic);
        checkEpicState(id);
        return id;
    }

    //добавим подзадачу
    public int addSubtask(Subtask subtask) {
        if (subtask == null) {
            return -1;
        }
        int epicId = subtask.getEpicId();
        Epic epic = epicMap.get(epicId);
        if (epic == null) {
            return -1;
        }
        int id = getNextId();
        subtask.setId(id);
        subtaskMap.put(id, subtask);
        // добавляем новую подзадачу к эпику
        epic.getSubtaskIds().add(id);
        // обновляем статус эпика
        checkEpicState(id);
        return id;
    }

    //методы обновления
    public int updateTask(Task task) {
        if (task == null) {
            return -1;
        }
        taskMap.put(task.getId(), task);
        return task.getId();
    }

    public long updateEpic(Epic epic) {
        if (epic == null) {
            return -1;
        }
        epicMap.put(epic.getId(), epic);
        checkEpicState(epic.getId()); // обновим статус эпика
        return epic.getId();
    }

    //методы обновления статуса
    public Task setTaskUpdateStatus(Task task, TaskStatus taskStatus) {
        if (task == null) {
            return null;
        }
        task.setTaskStatus(taskStatus);
        return task;
    }

    public Epic setEpicUpdateStatus(Epic epic, TaskStatus taskStatus) {
        if (epic == null) {
            return null;
        }
        epic.setTaskStatus(taskStatus);
        return epic;
    }

    public Subtask setSubtaskUpdateStatus(Subtask subtask, TaskStatus taskStatus) {
        if (subtask == null) {
            return null;
        }
        subtask.setTaskStatus(taskStatus);
        return subtask;
    }

    // рассчет статуса эпика
    private void checkEpicState(int id) {
        boolean flagNew = false;
        boolean flagInProgress = false;
        boolean flagDone = false;
        Epic epic = epicMap.get(id);
        if (epic == null) {
            return;
        }
        //по умолчанию статус NEW
        if (epic.getSubtaskIds().size() == 0) {
            epic.setTaskStatus(TaskStatus.NEW);
        }
        //проверяем статусы подзадачь
        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtaskMap.get(subtaskId);
            if (subtask == null) {
                continue;
            }
            switch (subtask.getTaskStatus()) {
                case NEW:
                    flagNew = true;
                    break;
                case IN_PROGRESS:
                    flagInProgress = true;
                    break;
                case DONE:
                    flagDone = true;
                    break;
            }
        }
        // если все подзадачи в статусе NEW, то статус эпика NEW
        if (!flagInProgress && !flagDone) {
            epic.setTaskStatus(TaskStatus.NEW);
        } else if (!flagNew && !flagInProgress) {
            // если все подзадачи в статусе DONE, то статус эпика DONE
            epic.setTaskStatus(TaskStatus.DONE);
        } else {
            // иначе статус эпика IN_PROGRESS
            epic.setTaskStatus(TaskStatus.IN_PROGRESS);
        }
    }

    // получить задачу по id
    public Task getTask(Integer id) {
        Task task = taskMap.get(id);
        return task;
    }

    // получить подзадачу по id
    public Subtask getSubtask(Integer id) {
        Subtask subtask = subtaskMap.get(id);
        return subtask;
    }

    // получить эпик по id
    public Epic getEpic(Integer id) {
        Epic epic = epicMap.get(id);
        return epic;
    }

    //удалим задачу
    public boolean removeTask(Integer id) {
        Task task = taskMap.remove(id);
        return (task != null);
    }

    //удалим подзадачу
    public boolean removeSubtask(Integer id) {
        Subtask subtask = subtaskMap.get(id);
        if (subtask == null) {//не нашли подзадачу
            return false;
        }
        Epic epic = epicMap.get(subtask.getEpicId());
        if (epic == null) {//не нашли эпик
            return false;
        }
        epic.getSubtaskIds().remove(id);
        checkEpicState(epic.getId());
        Subtask task = subtaskMap.remove(id);
        return (task != null);
    }

    public boolean removeEpic(Integer id) {
        Epic epic = epicMap.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                removeSubtask(subtaskId);
            }
        }
        return (epic != null);
    }

    //удилим все подзадачи
    public void removeAllSubtask() {
        subtaskMap.clear();
        epicMap.clear();
    }

    public void removeAllEpic() {
        epicMap.clear();
        subtaskMap.clear();
    }

    public Task getTaskId(Integer id) {
        return taskMap.get(id);
    }

    public void removeAllTask() {
        taskMap.clear();
    }

    public HashMap<Integer, Task> printTaskMap() {
        return taskMap;
    }

    public HashMap<Integer, Epic> printEpicMap() {
        return epicMap;
    }

    public HashMap<Integer, Subtask> printSubtaskMap() {
        return subtaskMap;
    }


}
