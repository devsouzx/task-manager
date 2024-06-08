package application;

import model.dao.DaoFactory;
import model.dao.TaskDao;
import model.entities.Task;
import model.entities.TaskService;
import model.entities.exceptions.TaskException;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Program {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    TaskService taskService = new TaskService();
    TaskDao taskDao = DaoFactory.createTaskDao();
    List<Task> tasks = taskDao.findAll();

    while (true) {
      UI.printList(taskDao.findAll(), (t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
      try {
        UI.printInterface();
        char command = sc.nextLine().charAt(0);

        switch (command) {
          case 'a':
            Task task = UI.getAddTaskInput(sc);
            taskDao.addTask(tasks, task);
            System.out.println("Task added!");
            break;
          case 'r':
            testList(tasks);
            UI.printList(tasks, (t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
            System.out.print("Which Task you what to remove (id)? ");
            int removeChoice = sc.nextInt();
            taskDao.deleteTask(removeChoice);
            System.out.println("Task removed!");
            break;
          case 'u':
            testList(tasks);
            UI.printList(tasks, (t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
            System.out.print("Which Task you what to update (id)? ");
            int id = sc.nextInt();
            taskDao.updateTask(taskService.getUpdateTask(taskDao, id));
            sc.nextLine();
            System.out.println("Task updated!");
            break;
          case 'd':
            testList(tasks);
            String strFile = UI.getDownloadFileInput(sc);
            taskService.downloadTask(strFile, tasks);
            System.out.println("Tasks downloaded with success!");
            break;
          default:
            throw new TaskException("Invalid Command!");
        }
      } catch (TaskException e) {
        System.out.println(e.getMessage());
        sc.nextLine();
      } catch (ParseException e) {
        throw new RuntimeException(e);
      } catch (RuntimeException e) {
        System.out.println("Invalid error! " + e.getMessage());
      }
    }
  }

  private static void testList(List<Task> tasks) {
    if (tasks.isEmpty()) {
      throw new TaskException("Tasks List is Empty!");
    }
  }
}
