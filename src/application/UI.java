package application;

import model.entities.Task;
import model.entities.enums.Priority;
import model.entities.enums.Status;
import model.entities.exceptions.TaskException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UI {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
  public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
  public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
  public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
  public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
  public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
  public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
  public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  public static void printInterface() {
    System.out.println("-------------------------------------------------");
    System.out.println("Task Manager");
    System.out.println("-------------------------------------------------");
    System.out.println("a - Add Task");
    System.out.println("r - Remove Task");
    System.out.println("u - Update Task");
    System.out.println("d - Download Task");
    System.out.println("-------------------------------------------------");
    System.out.print("Type command: ");
  }

  public static Task getAddTaskInput(Scanner sc, SimpleDateFormat sdf) throws ParseException {
    System.out.println("Add Task:");
    System.out.print("Title: ");
    String title = sc.nextLine();

    System.out.print("Description: ");
    String description = sc.nextLine();

    System.out.print("Due Date (dd/MM/yyyy): ");
    Date dueDate = sdf.parse(sc.nextLine());

    System.out.print("Priority (LOW/MEDIUM/HIGH): ");
    String priority = sc.nextLine().toUpperCase();
    if (!priority.equals("LOW") && !priority.equals("MEDIUM") && !priority.equals("HIGH")) {
      throw new TaskException("Invalid Priority! Valid Priorities are (LOW/MEDIUM/HIGH)");
    }
    System.out.print("Status (PENDING/ONGOING/COMPLETED): ");
    String status = sc.nextLine().toUpperCase();
    if (!status.equals("PENDING") && !status.equals("ONGOING") && !status.equals("COMPLETED")) {
      throw new TaskException("Invalid Status! Valid Statuses are (PENDING/ONGOING/COMPLETED)");
    }
    return new Task(title, description, dueDate, Priority.valueOf(priority), Status.valueOf(status));
  }

  public static int getRemoveTaskInput(Scanner sc, int tasksSize) {
    System.out.print("Which Task you what to remove (number)? ");
    int removeChoice = sc.nextInt();
    sc.nextLine();
    if (removeChoice > tasksSize || removeChoice < 1) {
      throw new TaskException("Your Task List just have " + tasksSize + " task(s)");
    }
    return removeChoice;
  }

  public static int getUpdateTaskInput(Scanner sc, int tasksSize) {
    System.out.print("Which Task you what to update (number)? ");
    int updateChoice = sc.nextInt();
    sc.nextLine();
    if (updateChoice > tasksSize || updateChoice < 1) {
      throw new TaskException("Your Task List just have " + tasksSize + " task(s)");
    }
    return updateChoice;
  }

  public static String getDownloadFileInput(Scanner sc) {
    System.out.println("type path (\\pasta):");
    return sc.nextLine();
  }

  public static void printList(List<Task> tasks, Comparator<Task> comparator) {
    tasks = tasks.stream().sorted(comparator).toList();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    System.out.println();
    System.out.println("-------------------------------------------------");
    if (tasks.isEmpty()) {
      System.out.println("Task List is Empty.");
    } else {
      for (int i = 0; i < tasks.size(); i++) {
        if (tasks.get(i).getStatus() == Status.COMPLETED) {
          System.out.print(ANSI_WHITE);
          printTask(tasks, sdf, i);
          System.out.print(ANSI_RESET);
        } else {
          printTask(tasks, sdf, i);
        }
        System.out.println("-------------------------------------------------");
      }
    }
  }

  public static void printTask(List<Task> tasks, SimpleDateFormat sdf, int i) {
    System.out.println("Task " + (i + 1) + ":");
    System.out.println("Title: " + tasks.get(i).getTitle());
    if (tasks.get(i).getDescription() != null) {
      System.out.println("Description: " + tasks.get(i).getDescription());
    }
    if (tasks.get(i).getDueDate() != null) {
      System.out.println("Due Date: " + sdf.format(tasks.get(i).getDueDate()));
    }
    System.out.println("Priority: " + tasks.get(i).getPriority());
    testStatus(i, tasks);
  }

  public static void testStatus(int i, List<Task> tasks) {
    if (tasks.get(i).getStatus() == Status.COMPLETED) {
      System.out.println("Status: " + ANSI_GREEN_BACKGROUND + ANSI_BLACK + tasks.get(i).getStatus() + ANSI_RESET);
    } else if (tasks.get(i).getStatus() == Status.PENDING) {
      System.out.println("Status: " + ANSI_RED_BACKGROUND + ANSI_BLACK + tasks.get(i).getStatus() + ANSI_RESET);
    } else {
      System.out.println("Status: " + ANSI_YELLOW_BACKGROUND + ANSI_BLACK + tasks.get(i).getStatus() + ANSI_RESET);
    }
  }
}
