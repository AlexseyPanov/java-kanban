public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        manager.addTask("Переезд", "Собрать коробки", TaskStatus.NEW);
        manager.addEpic("Важный эпик #1","Epic1", TaskStatus.NEW);
        manager.addSubtask("эпик #1 #1","test1", TaskStatus.NEW);
        manager.addSubtask("эпик #1 #2","test2", TaskStatus.NEW);
        manager.addSubtask("эпик #1 #3","test3", TaskStatus.NEW);

        manager.addEpic("Важный эпик #2","Epic2", TaskStatus.NEW);
        manager.addSubtask("эпик #2 Задача #1","test1", TaskStatus.NEW);

        System.out.println(manager.PrintTaskMap());

        manager.removeAllTask();

        System.out.println(manager.PrintTaskMap());

    }
}
