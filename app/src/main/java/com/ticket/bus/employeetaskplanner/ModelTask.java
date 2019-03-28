package com.ticket.bus.employeetaskplanner;

public class ModelTask {

    String taskId;
    String taskName;
    String boardName;
    String dateAdded;
    String taskDesc;
    String statusStatus;
    String taskDateCreated;
    String userId;



    public ModelTask(){

    }

    public ModelTask(String taskId, String taskName, String boardName, String dateAdded, String taskDesc, String statusStatus, String taskDateCreated, String userId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.boardName = boardName;
        this.dateAdded = dateAdded;
        this.taskDesc = taskDesc;
        this.statusStatus = statusStatus;
        this.taskDateCreated = taskDateCreated;
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public String getStatusStatus() {
        return statusStatus;
    }

    public String getTaskDateCreated() {
        return taskDateCreated;
    }

    public String getUserId() {
        return userId;
    }
}




