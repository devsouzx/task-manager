package model.entities;

import model.dao.TaskDao;
import model.entities.enums.Priority;
import model.entities.enums.Status;
import model.entities.exceptions.TaskException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskService {
  public Task getUpdateTask(TaskDao taskDao, Integer id) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Scanner sc = new Scanner(System.in);
    System.out.println("Update Task id = " + id + ":");
    System.out.print("What do you what to update? (title/description/duedate/priority/status)? ");
    String updateDecision = sc.nextLine();

    Task task = taskDao.findById(id);
    switch (updateDecision) {
      case "title":
        System.out.println("Actual Title: " + taskDao.findById(id).getTitle());
        System.out.print("New Title: ");
        String newTitle = sc.nextLine();
        task.setTitle(newTitle);
        break;
      case "description":
        System.out.println("Actual Description: " + taskDao.findById(id).getDescription());
        System.out.print("New Description: ");
        String newDescription = sc.nextLine();
        task.setDescription(newDescription);
        break;
      case "duedate":
        System.out.println("Actual Due Date: " + sdf.format(taskDao.findById(id).getDueDate()));
        System.out.print("New Due Date: ");
        Date newDueDate = sdf.parse(sc.nextLine());
        task.setDueDate(newDueDate);
        break;
      case "priority":
        System.out.println("Actual Priority: " + taskDao.findById(id).getPriority());
        System.out.print("New Priority: ");
        String newPriority = sc.nextLine().toUpperCase();
        if (!newPriority.equals("LOW") && !newPriority.equals("MEDIUM") && !newPriority.equals("HIGH")) {
          throw new TaskException("Invalid Priority! Valid Priorities are (LOW/MEDIUM/HIGH)");
        }
        task.setPriority(Priority.valueOf(newPriority));
        break;
      case "status":
        System.out.println("Actual Status: " + taskDao.findById(id).getStatus());
        System.out.print("New Status: ");
        String newStatus = sc.nextLine().toUpperCase();
        if (!newStatus.equals("PENDING") && !newStatus.equals("ONGOING") && !newStatus.equals("COMPLETED")) {
          throw new TaskException("Invalid Status! Valid Statuses are (PENDING/ONGOING/COMPLETED)");
        }
        task.setStatus(Status.valueOf(newStatus));
        break;
      default:
        throw new TaskException("Invalid Value!");
    }
    return task;
  }

  public void downloadTask(String strFile, List<Task> tasks) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(strFile + "\\tasks.txt"))) {
      bw.write("Tasks:");
      bw.newLine();
      for (int i = 0; i < tasks.size(); i++) {
        bw.write("Task " + (i + 1) + ":");
        bw.newLine();
        bw.write("Id: " + tasks.get(i).getId());
        bw.newLine();
        bw.write("Title: " + tasks.get(i).getTitle());
        bw.newLine();
        if (tasks.get(i).getDescription() != null) {
          bw.write("Description: " + tasks.get(i).getDescription());
          bw.newLine();
        }
        if (tasks.get(i).getDueDate() != null) {
          bw.write("Due Date: " + sdf.format(tasks.get(i).getDueDate()));
          bw.newLine();
        }
        bw.write("Priority: " + tasks.get(i).getPriority());
        bw.newLine();
        bw.write("Status: " + tasks.get(i).getStatus());
        bw.newLine();
        bw.newLine();
      }
    } catch (IOException e) {
      System.out.println("Error writing file: " + e.getMessage());
    }
  }
}
