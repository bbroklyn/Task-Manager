package ru.broklyn.taskmanager.repository.schema;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import ru.broklyn.taskmanager.repository.DataBase;
import ru.broklyn.taskmanager.utils.Colors;
import ru.broklyn.taskmanager.utils.DateUtils;

import java.time.Instant;
import java.util.Scanner;

public class CompletedTasks {
    public static void endTask() {
        System.out.println(Colors.COLOR_YELLOW + "[INPUT] " + Colors.COLOR_RESET + "Enter task name: ");
        Scanner scanner = new Scanner(System.in);
        String taskName = scanner.nextLine();

        DataBase dataBase = DataBase.getInstance();
        PersistentEntityStore entityStore = dataBase.getEntityStore();
        entityStore.executeInTransaction(txn -> {
            EntityIterable taskEntities = txn.find("Task", "taskName", taskName);
            Entity taskEntity = taskEntities.getFirst();


            if (taskEntity == null) {
                System.out.println(Colors.COLOR_RED + "[Error] " + Colors.COLOR_RESET + "Task not found: " + taskName);
            } else {
                try {
                    long currentTimeMillis = Instant.now().toEpochMilli();
                    Entity completedEntity = txn.newEntity("CompletedTasks");

                    completedEntity.addLink("Task", taskEntity);
                    completedEntity.setProperty("endtime", currentTimeMillis);

                    System.out.println(Colors.COLOR_GREEN + "[Successfully] " + Colors.COLOR_RESET + "Task marked as completed: " + taskName);
                } catch (Exception e) {
                    System.out.println(Colors.COLOR_RED + "[Error] " + Colors.COLOR_RESET + "Error marking task as completed: " + taskName);
                    e.printStackTrace();
                }
            }
            txn.commit();
        });
    }

    public static void showCompletedTasksWithTime() {
        DataBase dataBase = DataBase.getInstance();
        PersistentEntityStore entityStore = dataBase.getEntityStore();

        entityStore.executeInTransaction(txn -> {
            EntityIterable completedTaskEntities = txn.getAll("CompletedTasks");
            for (Entity completedEntity : completedTaskEntities) {
                Entity taskEntity = completedEntity.getLink("Task");
                Entity taskTimeEntity = completedEntity.getLink("TaskTime");

                if (taskEntity != null && taskTimeEntity != null) {
                    String taskName = (String) taskEntity.getProperty("taskName");

                    Long taskEndTime = (Long) completedEntity.getProperty("endtime");
                    String formattedTaskEndTime = taskEndTime != null ? DateUtils.formatInstant(Instant.ofEpochMilli(taskEndTime)) : "Unknown";

                    System.out.println("Task Name: " + taskName + ", Completed at: " + formattedTaskEndTime);
                }
            }
        });
    }

}
