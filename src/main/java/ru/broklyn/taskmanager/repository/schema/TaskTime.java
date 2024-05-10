package ru.broklyn.taskmanager.repository.schema;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import java.time.Instant;
import java.util.Scanner;
//
import ru.broklyn.taskmanager.repository.DataBase;
import ru.broklyn.taskmanager.utils.Colors;
import ru.broklyn.taskmanager.utils.DateUtils;


public class TaskTime {
    public static void startTask() {
        System.out.println(Colors.COLOR_YELLOW + "[INPUT] " + Colors.COLOR_RESET +
                "Enter task name: ");
        Scanner scanner = new Scanner(System.in);
        String taskName = scanner.nextLine();

        DataBase dataBase = DataBase.getInstance();
        PersistentEntityStore entityStore = dataBase.getEntityStore();
        entityStore.executeInTransaction(txn -> {
            EntityIterable taskEntities = txn.find("Task", "taskName", taskName);
            EntityIterable taskTimeEntities = txn.find("TaskTime", "task", taskName);

            Entity taskEntity = taskEntities.getFirst();
            if (taskEntity != null) {
                try {
                    long currentTimeMillis = Instant.now().toEpochMilli();
                    Entity newEntity = txn.newEntity("TaskTime");
                    newEntity.setProperty("time", currentTimeMillis);
                    newEntity.addLink("task", taskEntity);
                    System.out.println(Colors.COLOR_GREEN + "[Successfully] " + Colors.COLOR_RESET + "Time added for task: " + taskName);
                } catch (Exception e) {
                    System.out.println(Colors.COLOR_RED + "[Error] " + Colors.COLOR_RESET + "Error while adding time for task: " + taskName);
                    e.printStackTrace();
                }
            } else {
                System.out.println(Colors.COLOR_RED + "[Error] " + Colors.COLOR_RESET + "Task not found: " + taskName);
            }
            txn.commit();
        });
    }

    public static void showTasksWithTime() {
//        System.out.println(Colors.COLOR_GREEN + "[Started Tasks] " + Colors.COLOR_RESET +
//        "Started tasks," + Colors.COLOR_RED + " do not" + Colors.COLOR_RESET +  " forget about them:");

        DataBase dataBase = DataBase.getInstance();
        PersistentEntityStore entityStore = dataBase.getEntityStore();

        entityStore.executeInReadonlyTransaction(txn -> {
            EntityIterable entities = txn.getAll("TaskTime");
            try {
                for (Entity entity : entities) {
                    Long timeMillis = (Long) entity.getProperty("time");
                    if (timeMillis != null) {
                        Instant time = Instant.ofEpochMilli(timeMillis);
                        String formattedTime = DateUtils.formatInstant(time);
                        Entity taskEntity = entity.getLink("task");
                        String taskName = taskEntity != null ? (String) taskEntity.getProperty("taskName") : "Unknown";
                        System.out.println(Colors.COLOR_GREEN + "Task Name: " + Colors.COLOR_RESET + taskName +
                                Colors.COLOR_GREEN + ", Created at: " + Colors.COLOR_RESET + formattedTime);
                    } else {
                        System.out.println(Colors.COLOR_RED + "[Error]" + Colors.COLOR_RESET + "Time is null for TaskTime entity.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}


