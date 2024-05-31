package entities;

import entities.enums.Priority;
import entities.enums.Status;

import java.util.Date;

public class Task implements Comparable<Task> {
  private String title;
  private String description;
  private Date dueDate;
  private Priority priority;
  private Status status;

  public Task(String title, String description, Priority priority, Status status) {
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.status = status;
  }

  public Task(String title, String description, Date dueDate, Priority priority, Status status) {
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.priority = priority;
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public int compareTo(Task other) {
    return status.compareTo(other.getStatus());
  }
}
