package ru.yandex.app.model.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    static Epic epic;

    @BeforeEach
    void beforeEach() {
        epic = new Epic("TV", "LA");
    }

    @Test
    void getName(){
        assertEquals("TV",epic.getName());
    }

    @Test
    void setName(){
        assertEquals("TV",epic.getName());
        epic.setName("new name");
        assertEquals("new name",epic.getName());
    }
    @Test
    void getDescription() {
        assertEquals("LA", epic.getDescription());
    }

    @Test
    void setDescription() {
        assertEquals("LA", epic.getDescription());
        epic.setDescription("new description");
        assertEquals("new description", epic.getDescription());
    }

    @Test
    void getId() {
        assertEquals(0, epic.getId());
    }

    @Test
    void setId() {
        epic.setId(10);
        assertEquals(10, epic.getId());
    }

    @Test
    void getState() {
        assertEquals(TaskStatus.NEW, epic.getTaskStatus());
    }

    @Test
    void setState() {
        epic.setTaskStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.NEW, epic.getTaskStatus());
    }

    @Test
    void getSubtaskIds() {
        assertNotNull(epic.getSubtaskIds());
        assertEquals(0, epic.getSubtaskIds().size());
    }

    @Test
    void setSubtaskIds() {
        assertEquals(0, epic.getId());
        epic.setId(1);
        assertEquals(1, epic.getId());
    }

}