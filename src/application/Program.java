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
      System.out.println("what you what to do? ");
      System.out.println("Add Tasks (a)");
      System.out.println("Remove Tasks (r)");
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
      }
      System.out.println("Tasks: ");

      for (int i = 0; i < tasks.size(); i++) {
        System.out.println("# Task " + (i + 1) + ":");
        test(i, tasks ,sdf);
      }
    }
  }

  private static void test(int i, List<Task> tasks, SimpleDateFormat sdf) {
    System.out.println("Title: " + tasks.get(i).getTitle());
    if (tasks.get(i).getDescription() != null) {
      System.out.println("Description: " + tasks.get(i).getDescription());
    }
    if (tasks.get(i).getDueDate() != null) {
      System.out.println("Due Date: " + sdf.format(tasks.get(i).getDueDate()));
    }
    System.out.println("Priority: " + tasks.get(i).getPriority());
    System.out.println("Status: " + tasks.get(i).getStatus());
  }
}