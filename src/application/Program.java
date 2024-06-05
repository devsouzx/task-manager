package application;

import entities.Task;
import entities.TaskService;
import entities.exceptions.TaskException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Program {
  public static void main(String[] args) throws ParseException {
    Scanner sc = new Scanner(System.in);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    TaskService taskService = new TaskService();

    List<Task> tasks = new ArrayList<>();

    while (true) {
      try {
        UI.printInterface();
        char command = sc.nextLine().charAt(0);

        switch (command) {
          case 'a':
            tasks.add(UI.getAddTaskInput(sc, sdf));
            System.out.println("Task added successfully!");
            break;
          case 'r':
            if (tasks.isEmpty()) {
              throw new TaskException("Tasks List is Empty!");
            }
            UI.printList(tasks, (t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
            int removeChoice = UI.getRemoveTaskInput(sc, tasks.size());
            tasks.remove(removeChoice - 1);
            System.out.println("Task removed with success!");
            break;
          case 'u':
            if (tasks.isEmpty()) {
              throw new TaskException("Tasks List is Empty!");
            }
            UI.printList(tasks, (t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
            int updateChoice = UI.getUpdateTaskInput(sc, tasks.size());
            taskService.updateTask(tasks, updateChoice - 1, sc, sdf);
            System.out.println("Task updated with success!");
            break;
          case 'd':
            if (tasks.isEmpty()) {
              throw new TaskException("Tasks List is Empty!");
            }
            String strFile = UI.getDownloadFileInput(sc);
            taskService.downloadTask(strFile, tasks, sdf);
            System.out.println("Tasks downloaded with success!");
            break;
          default:
            throw new TaskException("Invalid Command! Valid commands are (a/r/u/d)");
        }

        UI.printList(tasks, (t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
      }
      catch (TaskException e) {
        System.out.println(e.getMessage());
        sc.nextLine();
      }
      catch (ParseException e) {
        System.out.println("Invalid Date!");
        sc.nextLine();
      }
      catch (RuntimeException e) {
        System.out.println("Invalid error!");
        sc.nextLine();
      }
    }
  }
}