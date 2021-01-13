package com.ttl.ritz7;

public class modelTask {
    public String name;
    public String imageURL;
    public  String taskAssigned;
    public  String time;

    public modelTask() {
    }

    public modelTask(String name, String imageURL, String taskAssigned, String time) {
        this.name = name;
        this.imageURL = imageURL;
        this.taskAssigned = taskAssigned;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTaskAssigned() {
        return taskAssigned;
    }

    public void setTaskAssigned(String taskAssigned) {
        this.taskAssigned = taskAssigned;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
