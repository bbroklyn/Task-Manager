package ru.broklyn.taskmanager.model;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import ru.broklyn.taskmanager.repository.DataBase;
import ru.broklyn.taskmanager.repository.schema.Task;
import ru.broklyn.taskmanager.utils.Colors;

import java.util.Scanner;

public class TaskModel {
    public static void addTask() {
        System.out.println("Enter tasks (type 'e' to finish):");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(Colors.COLOR_RED + "[Adding Task] "  + Colors.COLOR_RESET + "Task " +
                    Colors.COLOR_GREEN + "name: " + Colors.COLOR_RESET);
            String taskName = scanner.nextLine();

            if (taskName.equals("e")) {
                PrintModel.printMenu();
                break;
            } else {
                System.out.print(Colors.COLOR_RED + "[Adding Task] " + Colors.COLOR_RESET + "Task " +
                        Colors.COLOR_GREEN + "description: " + Colors.COLOR_RESET);
                String taskDescription = scanner.nextLine();

                Task.registerTasks(taskName, taskDescription);
            }
        }

        scanner.close();
    }

    public static void removeTask() {
        System.out.println(Colors.COLOR_YELLOW + "[INPUT] " + Colors.COLOR_RESET +
                "Enter task name: ");
        Scanner scanner = new Scanner(System.in);
        String taskToDelete = scanner.nextLine();

        DataBase dataBase = DataBase.getInstance();
        PersistentEntityStore entityStore = dataBase.getEntityStore();
        entityStore.executeInTransaction(txn -> {
            EntityIterable taskModelEntities = txn.find("Task", "taskName", taskToDelete);
            for (Entity entity : taskModelEntities) {
                try {
                    entity.delete();
                    System.out.println(Colors.COLOR_GREEN + "[Successfully]" + Colors.COLOR_RESET + " Task deleted: " + taskToDelete);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            txn.commit();
        });
    }

}

