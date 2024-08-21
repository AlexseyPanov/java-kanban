import ru.yandex.app.service.HistoryManager;
import ru.yandex.app.service.InMemoryTaskManager;
import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.Task;
import ru.yandex.app.service.Managers;
import ru.yandex.app.service.TaskManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        testCase3();
    }

    private static void testCase2() {
        // тестирование
        List<Task> history;
        Task task1 = new Task("task 1", "пример простой задачи 1");
        Task task2 = new Task("task 2", "пример простой задачи 2");
        //TaskManager manager = new InMemoryTaskManager();
        TaskManager manager = Managers.getDefault();
        //HistoryManager history = Managers.getDefaultHistory();
        int taskId1 = manager.addTask(task1);
        int taskId2 = manager.addTask(task2);

        manager.getTask(taskId1);
        manager.getTask(taskId2);
        history = manager.getHistory();
        System.out.println(history);

        //--

        Epic epic1 = new Epic("epic 1", "пример эпика 1");
        int epicId1 = manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("subtask 1", "пример подзадачи 1", epicId1);
        Subtask subtask2 = new Subtask("subtask 2", "пример подзадачи 2", epicId1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);


        manager.getSubtask(subtask1.getId());
        history = manager.getHistory();
        System.out.println(history);
        //--

        Epic epic2 = new Epic("epic 2", "пример эпика 2");
        int epicId2 = manager.addEpic(epic2);
        Subtask subtask3 = new Subtask("subtask 3", "пример подзадачи 3", epicId2);
        manager.addSubtask(subtask3);

    }

    // сценарий тестирования по спринту 7
    private static void testCase3() {
        // тестирование
        TaskManager manager = Managers.getDefault();

//        Task task1 = new Task("task 1", "пример простой задачи 1");

        // один эпик с 3 подзадачами
        Epic epic1 = new Epic("epic 1", "пример эпика 1");
        Integer epicId1 = manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("subtask 1", "пример подзадачи 1", epicId1);
        Subtask subtask2 = new Subtask("subtask 2", "пример подзадачи 2", epicId1);
        Subtask subtask3 = new Subtask("subtask 3", "пример подзадачи 3", epicId1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        Integer subtaskId1 = subtask1.getId();
        Integer subtaskId2 = subtask2.getId();
        Integer subtaskId3 = subtask3.getId();

        // эпик без подзадач
        Epic epic2 = new Epic("epic 2", "пример эпика 2");
        Integer epicId2 = manager.addEpic(epic2);

        /*
        запросите созданные задачи несколько раз в разном порядке;
        после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
        */

        manager.getSubtask(subtaskId2);
        System.out.println("must be: 'subtask 2'");
        printoutList(manager.getHistory());

        manager.getEpic(epicId2);
        System.out.println("must be: 'subtask 2', 'epic 2'");
        printoutList(manager.getHistory());

        manager.getEpic(epicId1);
        System.out.println("must be: 'subtask 2', 'epic 2', 'epic 1'");
        printoutList(manager.getHistory());

        manager.getSubtask(subtaskId1);
        System.out.println("must be: 'subtask 2', 'epic 2', 'epic 1', 'subtask 1'");
        printoutList(manager.getHistory());

        manager.getSubtask(subtaskId2);
        System.out.println("must be: 'epic 2', 'epic 1', 'subtask 1', 'subtask 2'");
        printoutList(manager.getHistory());

        manager.getEpic(epicId1);
        System.out.println("must be: 'epic 2', 'subtask 1', 'subtask 2', 'epic 1'");
        printoutList(manager.getHistory());

        // удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться
        manager.removeSubtask(subtaskId2);
        System.out.println("must be: 'epic 2', 'subtask 1', 'epic 1'");
        printoutList(manager.getHistory());

        // удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик,
        // так и все его подзадачи
        manager.removeEpic(epicId1);
        System.out.println("must be: 'epic 2'");
        printoutList(manager.getHistory());


    }

    // выводим список задач с указанием типа
    private static void printoutList(List<Task> list) {
        System.out.printf("%7s %4s %9s %15s %s\n", "type", "id", "state", "name", "description");
        for (Task task : list) {
            System.out.printf("%7s %4d %9s %15s %s\n",
                    task.getClass().getSimpleName(),
                    task.getId(), task.getTaskStatus(), task.getName(), task.getDescription());
        }

        System.out.println();
    }

    // выводим все списки задач менеджера по типам
    private static void printout(TaskManager manager) {
        System.out.println("--- Tasks ---");
        System.out.printf("%12s %9s %15s %s\n", "id", "state", "name", "description");
        for (Task task : manager.getAllTasks()) {
            System.out.printf("%12d %9s %15s %s\n",
                    task.getId(), task.getTaskStatus(), task.getName(), task.getDescription());
        }

        System.out.println();

        System.out.println("--- Epics ---");
        System.out.printf("%12s %9s %15s %s\n", "id", "state", "name", "description");
        for (Epic epic : manager.getAllEpics()) {
            System.out.printf("epic %7d %9s %15s %s\n",
                    epic.getId(), epic.getTaskStatus(), epic.getName(), epic.getDescription());
            for (Integer id : epic.getSubtaskIds()) {
                Subtask subtask = manager.getSubtask(id);
                System.out.printf("%12d %9s %15s %s\n",
                        subtask.getId(), subtask.getTaskStatus(), subtask.getName(), subtask.getDescription());
            }
        }
        System.out.println();
    }
}
