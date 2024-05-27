package application;

import entities.Task;
import entities.enums.Priority;
import entities.enums.Status;
import entities.exceptions.TaskException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class Program {
  public static void main(String[] args) throws ParseException {
    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);
    List<Task> tasks = new ArrayList<>();

    while (true) {
      try {
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
          if (!priority.equals("LOW") && !priority.equals("MEDIUM") && !priority.equals("HIGH")) {
            throw new TaskException("Invalid Priority! valid priorities are (low/medium/high)");
          }
          System.out.print("Status (pending/ongoing/completed): ");
          String status = sc.nextLine().toUpperCase();
          if (!status.equals("PENDING") && !status.equals("ONGOING") && !status.equals("COMPLETED")) {
            throw new TaskException("Invalid Status! valid status are (pending/ongoing/completed)");
          }
          Task task = new Task(title, description, dueDate, Priority.valueOf(priority), Status.valueOf(status));
          int position = 0;
          while (position < tasks.size() && (tasks.get(position).getStatus() != Status.COMPLETED)) {
            position++;
          }
          tasks.add(position, task);
          System.out.println();
          System.out.println("-------------------------------------------------");
          System.out.println();
        } else if (!tasks.isEmpty()) {
          if (command == 'r') {
            System.out.print("Which task you what to delete (number)? ");
            int removeChoice = sc.nextInt();
            if (removeChoice > tasks.size() || removeChoice < 0) {
              throw new TaskException("You task list just have " + tasks.size() + " task(s)");
            }
            tasks.remove(removeChoice - 1);
            sc.nextLine();
            System.out.println();
          } else if (command == 'u') {
            System.out.print("Which task you what to update (number)? ");
            int taskNumber = sc.nextInt();
            if (taskNumber > tasks.size() || taskNumber < 0) {
              throw new TaskException("You task list just have " + tasks.size() + " task(s)");
            }
            sc.nextLine();
            System.out.println("Update task " + taskNumber + ":");
            System.out.print("what do you what to update (title/description/duedate/priority/status)? ");
            String updateDecision = sc.nextLine();
            if (!updateDecision.equals("title") && !updateDecision.equals("description") && !updateDecision.equals("duedate") && !updateDecision.equals("priority") && !updateDecision.equals("status")) {
              throw new TaskException("Invalid Value!");
            }
            testUpdate(updateDecision, tasks, taskNumber);
          } else {
            throw new TaskException("Invalid command! valid commands are (a/r/u)");
          }
        } else {
          throw new TaskException("You dont have tasks!");
        }
        UI.printAll(tasks, sdf);
      } catch (TaskException e) {
        System.out.println(e.getMessage());
        sc.nextLine();
      } catch (ParseException e) {
        System.out.println("Invalid Date!");
        sc.nextLine();
      } catch (RuntimeException e) {
        System.out.println("Invalid error!");
        sc.nextLine();
      }
    }
  }

  private static void testUpdate(String updateDecision, List<Task> tasks, int taskNumber) throws ParseException {
    Scanner sc = new Scanner(System.in);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    if (updateDecision.equals("title")) {
      System.out.println("Actual Title: " + tasks.get(taskNumber - 1).getTitle());
      System.out.print("New Title: ");
      String ty = sc.nextLine();
      tasks.get(taskNumber - 1).setTitle(ty);
      System.out.println();
    } else if (updateDecision.equals("description")) {
      System.out.println("Actual Description: " + tasks.get(taskNumber - 1).getDescription());
      System.out.print("New Description: ");
      String tye = sc.nextLine();
      tasks.get(taskNumber - 1).setDescription(tye);
      System.out.println();
    } else if (updateDecision.equals("duedate")) {
      if (tasks.get(taskNumber - 1).getDueDate() == null) {
        tasks.get(taskNumber - 1).setDueDate(Date.from(Instant.now()));
      }
      System.out.println("Actual Due date: " + sdf.format(tasks.get(taskNumber - 1).getDueDate()));
      System.out.print("New Due date: ");
      Date tyc = sdf.parse(sc.nextLine());
      tasks.get(taskNumber - 1).setDueDate(tyc);
      System.out.println();
    } else if (updateDecision.equals("priority")) {
      System.out.println("Actual Priority: " + tasks.get(taskNumber - 1).getPriority());
      System.out.print("New Priority: ");
      String priority = sc.nextLine().toUpperCase();
      if (!priority.equals("LOW") && !priority.equals("MEDIUM") && !priority.equals("HIGH")) {
        throw new TaskException("Invalid Priority! valid priorities are (low/medium/high)");
      }
      Priority tye = Priority.valueOf(priority);
      tasks.get(taskNumber - 1).setPriority(tye);
      System.out.println();
    } else if (updateDecision.equals("status")) {
      System.out.println("Actual Status: " + tasks.get(taskNumber - 1).getStatus());
      System.out.print("New Status: ");
      String status = sc.nextLine().toUpperCase();
      if (!status.equals("PENDING") && !status.equals("ONGOING") && !status.equals("COMPLETED")) {
        throw new TaskException("Invalid Status! valid status are (pending/ongoing/completed)");
      }
      Status tye = Status.valueOf(status);
      tasks.get(taskNumber - 1).setStatus(tye);
      System.out.println();
    }
  }
}