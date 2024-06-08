package model.dao;

import model.entities.Task;

import java.util.List;

public interface TaskDao {
  void addTask(List<Task> tasks, Task task);
  void deleteTask(Integer id);
  void updateTask(Task task);
  Task findById(Integer id);
  List<Task> findAll();
}
