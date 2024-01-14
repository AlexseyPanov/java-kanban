import ru.yandex.app.service.TaskManager;
import ru.yandex.app.service.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
/*
        manager.addTask("Переезд", "Собрать коробки", TaskStatus.NEW);
        manager.addEpic("Важный эпик #1","Epic1", TaskStatus.NEW);
        manager.addSubTask();
        manager.addEpic("Важный эпик #2","Epic2", TaskStatus.NEW);
       */
        System.out.println(manager.PrintTaskMap());

        manager.removeAllTask();

        System.out.println(manager.PrintTaskMap());

    }
}
