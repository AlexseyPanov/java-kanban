package ru.yandex.app.service;

import ru.yandex.app.exception.ManagerLoadException;
import ru.yandex.app.exception.ManagerSaveException;
import ru.yandex.app.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 1. Создайте enum с типами задач. +
 * 2. Напишите метод сохранения задачи в строку String toString(Task task) или переопределите базовый. +
 * 3. Напишите метод создания задачи из строки Task fromString(String value).
 * 4. Напишите статические методы static String historyToString(HistoryManager manager) +
 * и static List<Integer> historyFromString(String value)
 * для сохранения и восстановления менеджера истории из табличного формата данных.
 */

public class FileBackedTaskManager extends InMemoryTaskManager {
    public File file;

    public FileBackedTaskManager() {
        String fileName = "resources/work/tasks.csv";
        file = new File(fileName);
        load();
    }

    public FileBackedTaskManager(File file) {
        this.file = file;
        load();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        return new FileBackedTaskManager(file);
    }

    protected void load() {
        if (!file.exists()) {
            return;
        }
        try {
            String line = Files.readString(Path.of(file.toURI()));
            restore(line);
        } catch (IOException e) {
            throw new ManagerLoadException("Error on load data", e);
        }
    }

    protected void restore(String str){
        String[] lines = str.split("\n");
        int maxId = -1;
        int num = 1;
        for(;num<lines.length && !lines[num].isBlank(); num++){
            Task task = fromString(lines[num]);
            maxId = Math.max(maxId, task.getId());
            if(task instanceof Epic){
                addEpic((Epic)task);
            } else if(task instanceof Subtask){
                Subtask subtask = (Subtask)task;
                addSubtask(subtask);
            } else {
                addTask(task);
            }
        }
        setId(maxId+1);
        num++;
        if(num< lines.length && !lines[num].isBlank()){
            List<Integer> historyIds = historyFromString(lines[num]);
            HistoryManager historyManager = getHistoryManager();
            for(Integer id: historyIds){
                Task task = getById(id);
                if(task!=null) {
                    historyManager.add(task);
                }
            }
        }
    }

    // метод сохранения задачи в строку
    protected String toString(Task task) {
        TaskType type = TaskType.TASK;
        if (task instanceof Epic) {
            type = TaskType.EPIC;
        } else if (task instanceof Subtask) {
            type = TaskType.SUB_TASK;
        }
        // id,type,name,status,description,epic
        String line = String.format("%d,%s,\"%s\",%s,\"%s\",%s",
                task.getId(), type, task.getName(), task.getTaskStatus(), task.getDescription(),
                (type == TaskType.SUB_TASK) ? ((Subtask) task).getEpicId() : "");
        return line;
    }

    protected String deQuote(String line) {
        return (line != null && line.startsWith("\"") && line.endsWith("\""))
                ? line.substring(1, line.length() - 1)
                : "";
    }

    protected Task fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            return null;
        }
        Integer id = Integer.parseInt(parts[0]);
        TaskType type = TaskType.valueOf(parts[1]);
        String name = deQuote(parts[2]);
        TaskStatus state = TaskStatus.valueOf(parts[3]);
        String desc = deQuote(parts[4]);
        Integer epicId = (parts.length > 5) ? Integer.parseInt(parts[5]) : -1;
        Task result;
        switch (type) {
            case SUB_TASK:
                Subtask subtask = new Subtask();
                subtask.setEpicId(epicId);
                result = subtask;
                break;
            case EPIC:
                result = new Epic();
                break;
            default:
                result = new Task();
        }

        result.setId(id);
        result.setName(name);
        result.setDescription(desc);
        result.setTaskStatus(state);

        return result;
    }

    private void writeTask(Task task, Writer fw) {
        try {
            fw.write(toString(task));
            fw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //сохранения и восстановления менеджера истории из табличного формата данных
    protected static String historyToString(HistoryManager manager) {
        String result = manager.getHistory().stream()
                .map(t -> String.valueOf(t.getId()))
                .collect(Collectors.joining(","));
        return result;
    }

    protected static List<Integer> historyFromString(String value) {
        List<Integer> result = Arrays.stream(value.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return result;
    }

    protected void save() {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("id,type,name,status,description,epic\n");
            getAllTasks().forEach(t -> writeTask(t, fw));
            getAllEpics().forEach(t -> writeTask(t, fw));
            getAllSubtasks().forEach(t -> writeTask(t, fw));
            fw.write("\n");
            fw.write(historyToString(getHistoryManager()));
            fw.write("\n");
        } catch (IOException e) {
            throw new ManagerSaveException("Error on save data", e);
        }
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeAllSubtask() {
        super.removeAllSubtask();
        save();
    }

    @Override
    public Task getTask(Integer id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtask(Integer id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Epic getEpic(Integer id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public int addTask(Task task) {
        Integer id = super.addTask(task);
        save();
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        Integer id = super.addEpic(epic);
        save();
        return id;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        Integer id = super.addSubtask(subtask);
        save();
        return id;
    }

    @Override
    public int updateTask(Task task) {
        Integer id = super.updateTask(task);
        save();
        return id;
    }

    @Override
    public int updateEpic(Epic epic) {
        Integer id = super.updateEpic(epic);
        save();
        return id;
    }

    @Override
    public boolean removeTask(Integer id) {
        boolean rez = super.removeTask(id);
        save();
        return rez;
    }

    @Override
    public boolean removeSubtask(Integer id) {
        boolean rez = super.removeSubtask(id);
        save();
        return rez;
    }

    @Override
    public boolean removeEpic(Integer id) {
        boolean rez = super.removeEpic(id);
        save();
        return rez;
    }

    public static void main(String[] args) {
        File f = new File("resources/test/testFiles.csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(f);

        System.out.println("чистим...");
        manager.removeAllTask();
        manager.removeAllSubtask();
        manager.removeAllEpic();
        manager.setId(0);
        System.out.println("----------");

        Task t1 = new Task("t1", "Задача 1");
        Task t2 = new Task("t2", "Задача 2");

        Integer t1Id = manager.addTask(t1);
        Integer t2Id = manager.addTask(t2);

        System.out.println("добавим эпик и две подзадачи");
        Epic e1 = new Epic("E-001", "Эпик 1");
        Integer epic1Id = manager.addEpic(e1);
        Subtask subtask1 = new Subtask("st1", "Подзадача 1", epic1Id);
        Subtask subtask2 = new Subtask("st2", "Подзадача 2", epic1Id);

        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        Integer subtask1Id = subtask1.getId();
        Integer subtask2Id = subtask2.getId();

        Epic e2 = new Epic("E-002", "Эпик 2");
        Integer epic2Id = manager.addEpic(e2);
        Subtask subtask3 = new Subtask("st3", "Подзадача 1 Эпика2", epic2Id);
        manager.addSubtask(subtask3);
        Integer subtask3Id = subtask3.getId();

        // Запросите некоторые из них, чтобы заполнилась история просмотра
        manager.getEpic(epic2Id);
        manager.getSubtask(subtask1Id);
        manager.getTask(t2Id);
        manager.getEpic(t1Id);
        manager.getSubtask(subtask2Id);

        // Создайте новый FileBackedTasksManager менеджер из этого же файла
        FileBackedTaskManager managerFileNew = new FileBackedTaskManager(f);

        // Проверьте, что история просмотра восстановилась верно и все задачи,
        // эпики, подзадачи, которые были в старом, есть в новом менеджере
        List<Task> tasksSrc = manager.getAllTasks();
        List<Task> tasksNew = managerFileNew.getAllTasks();

        System.out.println("Tasks :\t" + Objects.equals(tasksSrc, tasksNew));

        List<Subtask> subtasksSrc = manager.getAllSubtasks();
        List<Subtask> subtasksNew = managerFileNew.getAllSubtasks();

        System.out.println("Subtasks :\t" + Objects.equals(subtasksSrc, subtasksNew));

        List<Epic> epicsSrc = manager.getAllEpics();
        List<Epic> epicsNew = managerFileNew.getAllEpics();

        System.out.println("Epics :\t" + Objects.equals(epicsSrc, epicsNew));

        List<Task> historySrc = manager.getHistory();
        List<Task> historyNew = managerFileNew.getHistory();

        System.out.println("History :\t" + Objects.equals(historySrc, historyNew));
    }
}
