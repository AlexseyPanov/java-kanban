package ru.yandex.app.service;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
