package ru.yandex.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.Task;
import ru.yandex.app.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    static TaskManager manager;
    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefault();
        clear();
    }

    void clear() {
        // удалим все
        manager.removeAllTask();
        manager.removeAllEpic();
        manager.removeAllSubtask();
    }

    @Test
    void getAllTasks() {
        Task task1 = new Task("name1", "description");
        Task task2 = new Task("name2", "description2");
        Task task3 = new Task("name3", "description3");
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        List<Task> tasks = manager.getAllTasks();
        assertNotNull(tasks);
        assertEquals(3, tasks.size());
    }


    @Test
    void getTask() {
        Task task = new Task("name", "description");
        manager.addTask(task);
        assertEquals(task, manager.getTask(task.getId()));
        assertNull(manager.getTask(10));
    }

    @Test
    void createTask() {
        Task task = new Task("name", "description");
        task.setId(-1);
        assertEquals(-1, task.getId());
        int taskId = manager.addTask(task);
        assertEquals(task.getId(), taskId);
    }

    @Test
    void updateTask() {
        Task task = new Task("name", "description");
        int taskId = manager.addTask(task);
        task.setDescription("new description");
        manager.updateTask(task);
        Task task1 = manager.getTask(task.getId());
        assertEquals("new description", task1.getDescription());
    }

    @Test
    void removeTask() {
        Task task = new Task("name", "description");
        manager.addTask(task);
        Task task1 = manager.getTask(task.getId());
        assertNotNull(task1);

        manager.removeTask(task.getId());
        task1 = manager.getTask(task.getId());
        assertNull(task1);
    }

    @Test
    void getAllEpics() {
        Epic epic1 = new Epic("name1", "description");
        Epic epic2 = new Epic("name2", "description2");
        Epic epic3 = new Epic("name3", "description3");
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addEpic(epic3);
        List<Epic> epics = manager.getAllEpics();

        assertNotNull(epics);
        assertEquals(3, epics.size());
    }

    @Test
    void removeAllEpics() {
        Epic epic1 = new Epic("name1", "description");
        Epic epic2 = new Epic("name2", "description2");
        Epic epic3 = new Epic("name3", "description3");
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addEpic(epic3);
        List<Epic> epics = manager.getAllEpics();

        assertNotNull(epics);
        assertEquals(3, epics.size());

        manager.removeAllEpic();
        epics = manager.getAllEpics();

        assertEquals(0, epics.size());
    }

    @Test
    void getEpic() {
        Epic epic = new Epic("name", "description");
        manager.addEpic(epic);
        assertEquals(epic, manager.getEpic(epic.getId()));
        assertNull(manager.getEpic(10));
    }

    @Test
    void createEpic() {
        Epic epic = new Epic("name", "description");
        epic.setId(-1);
        assertEquals(-1, epic.getId());
        int taskId = manager.addEpic(epic);
        assertEquals(epic.getId(), taskId);
    }

    @Test
    void updateEpic() {
        Epic task = new Epic("name", "description");
        int taskId = manager.addEpic(task);
        task.setDescription("new description");
        manager.updateEpic(task);
        Epic task1 = manager.getEpic(task.getId());
        assertEquals("new description", task1.getDescription());
    }

    @Test
    void removeEpic() {
        Epic task = new Epic("name", "description");
        manager.addEpic(task);
        Epic task1 = manager.getEpic(task.getId());
        assertNotNull(task1);

        manager.removeEpic(task.getId());
        task1 = manager.getEpic(task.getId());
        assertNull(task1);
    }

    @Test
    void getAllSubtasks() {
        Epic epic1 = new Epic("name1", "description");
        Epic epic2 = new Epic("name2", "description2");
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        Subtask task1 = new Subtask("name3", "description3", epic1.getId());
        Subtask task2 = new Subtask("name4", "description4", epic1.getId());
        Subtask task3 = new Subtask("name5", "description5", epic2.getId());

        manager.addSubtask(task1);
        manager.addSubtask(task2);
        manager.addSubtask(task3);

        List<Subtask> list = manager.getAllSubtasks();

        assertNotNull(list);
        assertEquals(3, list.size());

        for (Subtask subtask : list) {
            int epicId = subtask.getEpicId();
            Epic epic = manager.getEpic(epicId);
            assertNotNull(epic);
        }
    }

    @Test
    void removeAllSubtasks() {
        Epic epic1 = new Epic("name1", "description");
        Epic epic2 = new Epic("name2", "description2");
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        Subtask task1 = new Subtask("name3", "description3", epic1.getId());
        Subtask task2 = new Subtask("name4", "description4", epic1.getId());
        Subtask task3 = new Subtask("name5", "description5", epic2.getId());

        manager.addSubtask(task1);
        manager.addSubtask(task2);
        manager.addSubtask(task3);

        List<Subtask> list = manager.getAllSubtasks();

        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals(2, epic1.getSubtaskIds().size());
        assertEquals(1, epic2.getSubtaskIds().size());

        manager.removeAllSubtask();
        list = manager.getAllSubtasks();

        assertEquals(0, list.size());
        assertEquals(0, epic1.getSubtaskIds().size());
        assertEquals(0, epic2.getSubtaskIds().size());
    }

    @Test
    void getSubtask() {
        Epic epic1 = new Epic("name1", "description");
        manager.addEpic(epic1);
        Subtask task = new Subtask("name3", "description3", epic1.getId());
        manager.addSubtask(task);

        assertEquals(task, manager.getSubtask(task.getId()));
        assertNull(manager.getSubtask(100));

        int epicId = task.getEpicId();
        Epic epic = manager.getEpic(epicId);
        assertNotNull(epic);
        assertEquals(epic1, epic);
    }

    @Test
    void createSubtask() {
        Epic epic1 = new Epic("name1", "description");
        manager.addEpic(epic1);
        Subtask task = new Subtask("name3", "description3", epic1.getId());
        task.setId(-1);
        assertEquals(-1, task.getId());
        int taskId = manager.addSubtask(task);
        assertEquals(task.getId(), taskId);

        int epicId = task.getEpicId();
        Epic epic = manager.getEpic(epicId);
        assertNotNull(epic);
        assertEquals(epic1, epic);
    }

    @Test
    void removeSubtask() {
        Epic epic1 = new Epic("name1", "description");
        manager.addEpic(epic1);
        Subtask task = new Subtask("name3", "description3", epic1.getId());
        int taskId = manager.addSubtask(task);

        Subtask task1 = manager.getSubtask(task.getId());
        assertNotNull(task1);

        manager.removeSubtask(task.getId());
        task1 = manager.getSubtask(task.getId());
        assertNull(task1);
    }

    @Test
    void getEpicSubtasks() {
        Epic epic1 = new Epic("name1", "description");
        Epic epic2 = new Epic("name2", "description2");
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        Subtask task1 = new Subtask("name3", "description3", epic1.getId());
        Subtask task2 = new Subtask("name4", "description4", epic1.getId());
        Subtask task3 = new Subtask("name5", "description5", epic2.getId());

        manager.addSubtask(task1);
        manager.addSubtask(task2);
        manager.addSubtask(task3);

        List<Subtask> list = manager.getSubtasksByEpicId(epic1.getId());
        assertEquals(List.of(task1, task2), list);
    }

    @Test
    void getHistory() {
        Task task = new Task("name1", "description1");
        Epic epic = new Epic("name2", "description2");
        //добавим задачи
        manager.addTask(task);
        manager.addEpic(epic);

        Subtask subtask = new Subtask("name3", "description3", epic.getId());
        manager.addSubtask(subtask);

        //получим список
        List<Task> history = manager.getHistory();
        assertNotNull(history,"Задачи не возвращаются.");
        assertEquals(0, history.size(),"Неверное количество задач.");

        //По id посмотрим задачу, в истории появится одна задача
        manager.getTask(task.getId());
        history = manager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));

        //По id посмотрим эпик, в истории появится плюс один эпик
        manager.getEpic(epic.getId());
        history = manager.getHistory();
        assertEquals(List.of(task, epic), history);

        //По id посмотрим подзадачу, в истории появится три экземпляра: таск,эпик,подзадача
        manager.getSubtask(subtask.getId());
        history = manager.getHistory();
        assertEquals(List.of(task, epic, subtask), history);

    }

    @Test
    public void calculateStatusEpmty() {
        Epic epic = new Epic("name1", "description1");
        manager.addEpic(epic);
        // Пустой список подзадач
        assertEquals(TaskStatus.NEW, epic.getTaskStatus());
    }

    @Test
    public void calculateStatusAllNEW() {
        Epic epic = new Epic("name1", "description1");
        manager.addEpic(epic);

        epic.setTaskStatus(TaskStatus.DONE);

        Subtask subTask1 = new Subtask("subtask1", "info1", epic.getId());
        Subtask subTask2 = new Subtask("subtask2", "info2", epic.getId());
        Subtask subTask3 = new Subtask("subtask3", "info3", epic.getId());

        manager.addSubtask(subTask1);
        manager.addSubtask(subTask2);
        manager.addSubtask(subTask3);

        assertEquals(TaskStatus.DONE, epic.getTaskStatus());
    }
}