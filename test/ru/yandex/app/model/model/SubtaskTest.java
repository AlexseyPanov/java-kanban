package ru.yandex.app.model.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    static Epic epic;
    static Subtask subtask;

    @BeforeEach
    void beforeEach() {
        epic = new Epic("nameEpicOne","LaLaLa");
        subtask = new Subtask("nameSubtask","Subtask LALALA",epic.getId());
    }

    @Test
    void getId() {
        assertEquals(0, subtask.getEpicId());
    }

    @Test
    void setId() {
        assertEquals(0, subtask.getEpicId());
        subtask.setEpicId(1);
        assertEquals(1, subtask.getEpicId(),"ID подзадачи не равна");
    }

    @Test
    void getEpicId_MinusOne() {
        assertNotEquals(-1, subtask.getEpicId());
    }

    @Test
    void setEpicIdMaxValue() {
        assertEquals(0, subtask.getEpicId());
        subtask.setEpicId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, subtask.getEpicId());
    }

    @Test
    void getName() {
        assertEquals("nameSubtask", subtask.getName());
    }

    @Test
    void setName() {
        assertEquals("nameSubtask", subtask.getName());
        subtask.setName("new name");
        assertEquals("new name", subtask.getName());
    }

    @Test
    void getDescription() {
        assertEquals("Subtask LALALA", subtask.getDescription());
    }

    @Test
    void setDescription() {
        assertEquals("Subtask LALALA", subtask.getDescription());
        subtask.setDescription("new description");
        assertEquals("new description", subtask.getDescription());
    }

    @Test
    void getState() {
        assertEquals(TaskStatus.NEW, subtask.getTaskStatus());
    }

    @Test
    void setState() {
        subtask.setTaskStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, subtask.getTaskStatus());
    }

    @Test
    void getEpic() {
        assertEquals(epic.getId(), subtask.getEpicId());
    }

    @Test
    void setEpic() {
        assertEquals(epic.getId(), subtask.getEpicId());
        Epic epic2 = new Epic("epic2", "info2");
        subtask.setEpicId(epic2.getId());
        assertEquals(epic2.getId(), subtask.getEpicId());
    }
}