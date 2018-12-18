package com.cnpiec.ireader.model;

import java.util.Date;

public class TaskJournal {
    private String taskId;
    private String name;
    private Date created;
    private String memo;
    private int copy;
    
    public int getCopy() {
        return copy;
    }
    public void setCopy(int copy) {
        this.copy = copy;
    }
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
}
