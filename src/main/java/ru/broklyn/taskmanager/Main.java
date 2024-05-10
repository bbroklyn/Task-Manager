package ru.broklyn.taskmanager;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import org.slf4j.LoggerFactory;
import ru.broklyn.taskmanager.model.PrintModel;
import ru.broklyn.taskmanager.repository.DataBase;

import java.time.Instant;

import static ru.broklyn.taskmanager.repository.schema.CompletedTasks.showCompletedTasksWithTime;

public class Main {
    public static void main(String[] args) {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ERROR);
        PrintModel.printMenu();
    }

}
