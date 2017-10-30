package com.kanban.model.request;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by david on 28-Oct-17.
 */
public class TaskRequest implements Serializable {

    private String taskId;
    private String name;
    private String description;
    private Long boardColumnId;
    private String color;
    private Date dueDate;

    public TaskRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBoardColumnId() {
        return boardColumnId;
    }

    public void setBoardColumnId(Long boardColumnId) {
        this.boardColumnId = boardColumnId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
