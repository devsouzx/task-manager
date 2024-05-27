package application;

import entities.Task;
import entities.enums.Status;

import java.text.SimpleDateFormat;
import java.util.List;

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

  public static void printAll(List<Task> tasks, SimpleDateFormat sdf) {
    System.out.println("Tasks: ");
    for (int i = 0; i < tasks.size(); i++) {
      if (tasks.get(i).getStatus() == Status.COMPLETED) {
        System.out.print(ANSI_WHITE);
        System.out.println("# Task " + (i + 1) + ":");
        print(i, tasks, sdf);
        System.out.print(ANSI_RESET);
      } else {
        print(i, tasks, sdf);
      }
    }
  }

  public static void print(int i, List<Task> tasks, SimpleDateFormat sdf) {
    System.out.println("Title: " + tasks.get(i).getTitle());
    if (tasks.get(i).getDescription() != null) {
      System.out.println("Description: " + tasks.get(i).getDescription());
    }
    if (tasks.get(i).getDueDate() != null) {
      System.out.println("Due Date: " + sdf.format(tasks.get(i).getDueDate()));
    }
    System.out.println("Priority: " + tasks.get(i).getPriority());
    if (tasks.get(i).getStatus() == Status.COMPLETED) {
      System.out.println("Status: " + ANSI_GREEN_BACKGROUND + ANSI_BLACK + tasks.get(i).getStatus() + ANSI_RESET);
    } else if (tasks.get(i).getStatus() == Status.PENDING) {
      System.out.println("Status: " + ANSI_RED_BACKGROUND + ANSI_BLACK + tasks.get(i).getStatus() + ANSI_RESET);
    } else {
      System.out.println("Status: " + ANSI_YELLOW_BACKGROUND + ANSI_BLACK + tasks.get(i).getStatus() + ANSI_RESET);
    }
  }
}
