
import ru.yandex.app.service.TaskManager;
import ru.yandex.app.service.TaskStatus;
import ru.yandex.app.model.Epic;
import ru.yandex.app.model.Subtask;
import ru.yandex.app.model.Task;


public class Main {
    public static void main(String[] args) {

        /*
         * 1. Не знаю как обратную связь наладить +
         * 2. TaskManager - Что значить "записать его в МАПУ"? +
         * 3.
         * */

        Task task1 = new Task("task 1", "пример простой задачи 1");
        Task task2 = new Task("task 2", "пример простой задачи 2");
        TaskManager manager = new TaskManager();
        int taskId1 = manager.addTask(task1);
        int taskId2 = manager.addTask(task2);

        //--

        Epic epic1 = new Epic("epic 1", "пример эпика 1");
        int epicId1 = manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("subtask 1", "пример подзадачи 1", epicId1);
        Subtask subtask2 = new Subtask("subtask 2", "пример подзадачи 2", epicId1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        //--

        Epic epic2 = new Epic("epic 2", "пример эпика 2");
        int epicId2 = manager.addEpic(epic2);
        Subtask subtask3 = new Subtask("subtask 3", "пример подзадачи 3", epicId2);
        manager.addSubtask(subtask3);

        //--

        System.out.println(manager.getPrintTaskMap());
        System.out.println(manager.getPrintEpicMap());
        System.out.println(manager.getPrintSubtaskMap());


    }
}
