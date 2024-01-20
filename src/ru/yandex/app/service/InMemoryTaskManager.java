package ru.yandex.app.service;

import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.Task;
import ru.yandex.app.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private int id = 0;
    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    // менеджер истории просмотров
    private final HistoryManager historyManagers = Managers.getDefaultHistory();

    // получить новый ID
    private int getNextId() {
        return id++;
    }

    @Override
    public List<Task> getHistory() {
        return historyManagers.getHistory();
    }

    // получить задачу по id
    @Override
    public Task getTask(Integer id) {
        Task task = taskMap.get(id);
        //Добавим в историю просмотров
        historyManagers.add(task);
        return task;
    }

    // получить подзадачу по id
    @Override
    public Subtask getSubtask(Integer id) {
        Subtask subtask = subtaskMap.get(id);
        //Добавим в историю просмотров
        historyManagers.add(subtask);
        return subtask;
    }

    // получить эпик по id
    @Override
    public Epic getEpic(Integer id) {
        Epic epic = epicMap.get(id);
        //Добавим в историю просмотров
        historyManagers.add(epic);
        return epic;
    }

    //добавим задачу
    @Override
    public int addTask(Task task) {
        if (task == null) {
            return -1;
        }
        int id = getNextId();
        task.setId(id);
        taskMap.put(id, task);
        return id;
    }

    //добавим эпик
    @Override
    public int addEpic(Epic epic) {
        if (epic == null) {
            return -1;
        }
        int id = getNextId();
        epic.setId(id);
        epicMap.put(id, epic);
        checkEpicState(id);
        return id;
    }

    //добавим подзадачу
    @Override
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
    @Override
    public int updateTask(Task task) {
        if (task == null) {
            return -1;
        }
        taskMap.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public long updateEpic(Epic epic) {
        if (epic == null) {
            return -1;
        }
        Epic oldEpic = epicMap.get(epic.getId());
        oldEpic.setName(epic.getName());
        oldEpic.setDescription(epic.getDescription());
        return epic.getId();
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
            return;
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


    //метод получения всех подзадач эпика по его идентификатору
    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epicMap.get(epicId);
        if (epic == null) {
            return result;
        }
        for (int id : epic.getSubtaskIds()) {
            Subtask subtask = subtaskMap.get(id);
            if (subtask != null) {
                result.add(subtask);
            }
        }
        return result;
    }

    //удалим задачу
    @Override
    public boolean removeTask(Integer id) {
        Task task = taskMap.remove(id);
        return (task != null);
    }

    //удалим подзадачу
    @Override
    public boolean removeSubtask(Integer id) {
        Subtask subtask = subtaskMap.remove(id);
        if (subtask == null) {//не нашли подзадачу
            return false;
        }
        Epic epic = epicMap.get(subtask.getEpicId());
        if (epic == null) {//не нашли эпик
            return false;
        }
        epic.getSubtaskIds().remove(id);
        checkEpicState(epic.getId());
        return true;
    }

    @Override
    public boolean removeEpic(Integer id) {
        Epic epic = epicMap.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtaskMap.remove(subtaskId);
            }
        }
        return (epic != null);
    }

    //Удалим все подзадачи
    //При удалении всех подзадач, удаление эпиков осуществлять - не требуется.
    //Следует лишь пройтись по ним и почистить в них списки идентификаторов подзадач и обновить их статусы.
    @Override
    public void removeAllSubtask() {
        subtaskMap.clear();
        for (Epic epic : epicMap.values()) {
            epic.getSubtaskIds().clear();
            checkEpicState(epic.getId());
        }
    }

    @Override
    public void removeAllEpic() {
        epicMap.clear();
        subtaskMap.clear();
    }

    @Override
    public void removeAllTask() {
        taskMap.clear();
    }

    @Override
    public ArrayList<Task> getPrintTaskMap() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public ArrayList<Epic> getPrintEpicMap() {
        return new ArrayList<>(epicMap.values());
    }

    @Override
    public ArrayList<Subtask> getPrintSubtaskMap() {
        return new ArrayList<>(subtaskMap.values());
    }


}
