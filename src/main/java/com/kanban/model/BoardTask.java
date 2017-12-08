package com.kanban.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kanban.utils.CustomerDateAndTimeDeserialize;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class BoardTask extends AbstractEntity {

    private String taskId;
    private String name;
    private String description;
    private String color;
    private Long boardColumnId;
    private Long boardId;
    private String assignee;
    private String owner;
    private String followUpDate;
    private String processInstanceId;
    private boolean deleted = false;
    private Date createDate;
    private Date updateDate;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
    private Date dueDate;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getBoardColumnId() {
        return boardColumnId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public BoardTask() {
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBoardColumnId(Long boardColumnId) {
        this.boardColumnId = boardColumnId;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
