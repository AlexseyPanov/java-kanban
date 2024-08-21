package ru.yandex.app.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.app.model.Task;
import ru.yandex.app.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    static Task task;

    @BeforeEach
    void beforeEach() {
        task = new Task("TaskName", "TaskDescription");
    }

    @Test
    void getName() {
        assertEquals("TaskName", task.getName(),"Таски не равны");
    }

    @Test
    void setName() {
        assertEquals("TaskName", task.getName());
        task.setName("TaskName New");
        assertEquals("TaskName New", task.getName());
    }

    @Test
    void getDescription() {
        assertEquals("TaskDescription", task.getDescription());
    }

    @Test
    void setDescription() {
        assertEquals("TaskDescription", task.getDescription());
        task.setDescription("TaskDescription new");
        assertEquals("TaskDescription new", task.getDescription());
    }

    @Test
    void getTaskStatus() {
        assertEquals(TaskStatus.NEW, task.getTaskStatus());
    }

    @Test
    void setTaskStatusInProgress() {
        assertEquals(TaskStatus.NEW, task.getTaskStatus());
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getTaskStatus());
    }

    @Test
    void setTaskStatusDone() {
        assertEquals(TaskStatus.NEW, task.getTaskStatus());
        task.setTaskStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getTaskStatus());
    }

    @Test
    void getId() {
        assertEquals(0, task.getId());
    }

    @Test
    void setId() {
        assertEquals(0, task.getId());
        task.setId(99999);
        assertEquals(99999, task.getId());
    }

    @Test
    void setIdMaxInteger() {
        assertEquals(0, task.getId());
        task.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, task.getId());
    }
}