package model.dao.implementation;

import db.DB;
import db.DbException;
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
  public void addTask(List<Task> tasks, Task task) {
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

      tasks.add(task);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DB.closeStatement(statement);
    }
  }

  @Override
  public void deleteTask(Integer id) {
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      statement = connection.prepareStatement("DELETE FROM tasks WHERE Id = ?");

      statement.setInt(1, id);

      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DB.closeStatement(statement);
    }
  }

  @Override
  public Task findById(Integer id) {
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      statement = connection.prepareStatement("SELECT tasks.* FROM tasks WHERE id = ?");

      statement.setInt(1, id);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        Task task = instantiateTask(resultSet);
        return task;
      }
      return null;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(statement);
      DB.closeResultSet(resultSet);
    }
  }

  @Override
  public void updateTask(Task task) {
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      statement = connection.prepareStatement("UPDATE tasks SET title = ?, description = ?, status = ?, priority = ?, due_date = ? WHERE id = ?");
      statement.setString(1, task.getTitle());
      statement.setString(2, task.getDescription());
      statement.setString(3, task.getStatus().toString().toLowerCase());
      statement.setString(4, task.getPriority().toString().toLowerCase());
      statement.setDate(5, new java.sql.Date(task.getDueDate().getTime()));
      statement.setInt(6, task.getId());

      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
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
        task.setId(resultSet.getInt("id"));
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

  private Task instantiateTask(ResultSet resultSet) throws SQLException {
    Task task = new Task();
    task.setId(resultSet.getInt("id"));
    task.setTitle(resultSet.getString("title"));
    task.setDescription(resultSet.getString("description"));
    task.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
    task.setPriority(Priority.valueOf(resultSet.getString("priority").toUpperCase()));
    task.setDueDate(resultSet.getDate("due_date"));
    return task;
  }
}
