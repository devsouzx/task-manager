package model.dao.implementation;

import db.DB;
import model.dao.TaskDao;
import model.entities.Task;
import model.entities.enums.Priority;
import model.entities.enums.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoJDBC implements TaskDao {
  private Connection connection;

  public TaskDaoJDBC(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void addTask(Task task) {
    PreparedStatement statement = null;

    try {
      statement = connection.prepareStatement(
              "INSERT INTO tasks (title, description, status, priority, due_date) VALUES (?, ?, ?, ?, ?)"
      );

      statement.setString(1, task.getTitle());
      statement.setString(2, task.getDescription());
      statement.setString(3, task.getStatus().toString().toLowerCase());
      statement.setString(4, task.getPriority().toString().toLowerCase());
      statement.setDate(5, new java.sql.Date(task.getDueDate().getTime()));

      int rows = statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DB.closeStatement(statement);
    }
  }

  @Override
  public void deleteTask(Integer id) {

  }

  @Override
  public void updateTask(Task task) {
  }

  @Override
  public Task findById(Integer id) {
    return null;
  }

  @Override
  public List<Task> findAll() {
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      statement = connection.prepareStatement("SELECT tasks.* FROM tasks ORDER BY id");

      resultSet = statement.executeQuery();
      List<Task> tasks = new ArrayList<>();

      while (resultSet.next()) {
        Task task = new Task();
        task.setTitle(resultSet.getString("title"));
        task.setDescription(resultSet.getString("description"));
        task.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
        task.setPriority(Priority.valueOf(resultSet.getString("priority").toUpperCase()));
        task.setDueDate(new java.sql.Date(resultSet.getDate("due_date").getTime()));
        tasks.add(task);
      }
      return tasks;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DB.closeStatement(statement);
      DB.closeResultSet(resultSet);
    }
  }
}
