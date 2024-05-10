package ru.broklyn.taskmanager.model;

import ru.broklyn.taskmanager.repository.schema.CompletedTasks;
import ru.broklyn.taskmanager.repository.schema.Task;
import ru.broklyn.taskmanager.repository.schema.TaskTime;
import ru.broklyn.taskmanager.utils.Colors;

import java.util.Scanner;

public class PrintModel {

    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println(Colors.COLOR_GREEN + "Choose the action:\n" +
                    Colors.COLOR_WHITE + "1. " + Colors.COLOR_RESET + "Add task\n" +
                    Colors.COLOR_WHITE + "2. " + Colors.COLOR_RESET + "Delete task\n" +
                    Colors.COLOR_WHITE + "3. " + Colors.COLOR_RESET + "Check all tasks\n\n" +

                    Colors.COLOR_WHITE + "4. " + Colors.COLOR_RESET + "Start with task\n" +
                    Colors.COLOR_WHITE + "5. " + Colors.COLOR_RESET + "Show started tasks\n\n" +

                    Colors.COLOR_WHITE + "6. " + Colors.COLOR_RESET + "End with task\n" +
                    Colors.COLOR_WHITE + "7. " + Colors.COLOR_RESET + "Show completed tasks\n\n" +


                    Colors.COLOR_RED + "0. " + Colors.COLOR_RESET + "Quit\n" +
                    Colors.COLOR_WHITE + "Your choice: " + Colors.COLOR_RESET);

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    TaskModel.addTask();
                    break;
                case 2:
                    TaskModel.removeTask();
                    break;
                case 3:
                    Task.showTasks();
                    break;
                case 4:
                    TaskTime.startTask();
                    break;
                case 5:
                    TaskTime.showTasksWithTime();
                    break;
                case 6:
                    CompletedTasks.endTask();
                    break;
                case 7:
                    CompletedTasks.showCompletedTasksWithTime();
                    break;
                case 0:
                    System.out.println(Colors.COLOR_RED + "[Exit] " + Colors.COLOR_RESET + "Exiting TaskManager.");
                    break;
                default:
                    System.out.println(Colors.COLOR_RED + "[Error] " + Colors.COLOR_RESET + "Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }
}
