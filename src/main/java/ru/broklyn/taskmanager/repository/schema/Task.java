package ru.broklyn.taskmanager.repository.schema;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import org.jetbrains.annotations.Nullable;
import ru.broklyn.taskmanager.repository.DataBase;
import ru.broklyn.taskmanager.utils.Colors;

public class Task {
    public static void registerTasks(String taskName, String taskDescription) {
        DataBase dataBase = DataBase.getInstance();
        PersistentEntityStore entityStore = dataBase.getEntityStore();
        entityStore.executeInTransaction(txn -> {
            Entity registerTask = txn.newEntity("Task");
            registerTask.setProperty("taskName", taskName);
            registerTask.setProperty("taskDescription", taskDescription);
            System.out.println(Colors.COLOR_GREEN + "[Successfully] " + Colors.COLOR_RESET + "task added with name: " + taskName);
            txn.commit();
        });
    }

    public static void showTasks() {
        DataBase dataBase = DataBase.getInstance();
        PersistentEntityStore entityStore = dataBase.getEntityStore();
        entityStore.executeInReadonlyTransaction(txn -> {
            EntityIterable entities = txn.getAll("Task");
            for (Entity entity : entities) {
                String taskName = (String) entity.getProperty("taskName");
                String taskDescription = (String) entity.getProperty("taskDescription");
                System.out.println(Colors.COLOR_RED + "[List] " +
                        Colors.COLOR_GREEN + "Name " + Colors.COLOR_RESET + taskName +
                        Colors.COLOR_GREEN + ", Description: " + Colors.COLOR_RESET + taskDescription);
            }
            // TODO: -> Completed tasks here:
        });
    }
}
