package application;

import entities.Task;
import entities.enums.Priority;
import entities.enums.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Program {
  public static void main(String[] args) throws ParseException {
    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);
    List<Task> tasks = new ArrayList<>();


    while (true) {
      System.out.println();
      System.out.println("#Task Manager:");
      System.out.println("What you what to do? ");
      System.out.println("Add Tasks (a)");
      System.out.println("Remove Tasks (r)");
      System.out.println("Update Tasks (u)");
      char command = sc.nextLine().charAt(0);
      String description = null;
      Date dueDate = null;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      if (command == 'a') {
        System.out.println("Add Task:");
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description? (y/n) ");
        if (sc.nextLine().charAt(0) == 'y') {
          System.out.print("Description: ");
          description = sc.nextLine();
        }
        System.out.print("Due Date? (y/n) ");
        if (sc.nextLine().charAt(0) == 'y') {
          System.out.print("Due Date (dd/mm/yyyy): ");
          dueDate = sdf.parse(sc.nextLine());
        }
        System.out.print("Priority (low/medium/high): ");
        String priority = sc.nextLine().toUpperCase();
        System.out.print("Status (pending/ongoing/completed): ");
        String status = sc.nextLine().toUpperCase();
        Task task = new Task(title, description, dueDate, Priority.valueOf(priority), Status.valueOf(status));
        int position = 0;
        while (position < tasks.size() && (tasks.get(position).getStatus() != Status.COMPLETED)) {
          position++;
        }
        tasks.add(position, task);
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println();
      } else if (command == 'r') {
        System.out.print("Which task you what to delete (number)? ");
        tasks.remove(sc.nextInt() - 1);
        sc.nextLine();
        System.out.println();
      } else {
        System.out.print("Which task you what to update (number)? ");
        int number = sc.nextInt();
        sc.nextLine();
        System.out.println("Update task " + number + ":");
        System.out.print("what do you what to update (title/description/duedate/priority/status)? ");
        String tets = sc.nextLine();
        if (tets.equals("title")) {
          System.out.println("Actual Title: " + tasks.get(number - 1).getTitle());
          System.out.print("New Title: ");
          String ty = sc.nextLine();
          tasks.get(number - 1).setTitle(ty);
          System.out.println();
        } else if (tets.equals("description")) {
          System.out.println("Actual Description: " + tasks.get(number - 1).getDescription());
          System.out.print("New Description: ");
          String tye = sc.nextLine();
          tasks.get(number - 1).setDescription(tye);
          System.out.println();
        } else if (tets.equals("duedate")) {
          System.out.println("Actual Due date: " + sdf.format(tasks.get(number - 1).getDueDate()));
          System.out.print("New Due date: ");
          Date tyc = sdf.parse(sc.nextLine());
          tasks.get(number - 1).setDueDate(tyc);
          System.out.println();
        } else if (tets.equals("priority")) {
          System.out.println("Actual Priority: " + tasks.get(number - 1).getPriority());
          System.out.print("New Priority: ");
          Priority tye = Priority.valueOf(sc.nextLine().toUpperCase());
          tasks.get(number - 1).setPriority(tye);
          System.out.println();
        } else if (tets.equals("status")) {
          System.out.println("Actual Status: " + tasks.get(number - 1).getStatus());
          System.out.print("New Status: ");
          Status tye = Status.valueOf(sc.nextLine().toUpperCase());
          tasks.get(number - 1).setStatus(tye);
          System.out.println();
        }
      }

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
  }

  private static void print(int i, List<Task> tasks, SimpleDateFormat sdf) {
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
}