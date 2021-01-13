package com.ttl.ritz7;

public class modelProject {
    public String projectName;
    public String imageURLpro;
    public  String proDescription;
    public  String proCost;

    public modelProject() {
    }

    public modelProject(String projectName, String imageURLpro, String proDescription, String proCost) {
        this.projectName = projectName;
        this.imageURLpro = imageURLpro;
        this.proDescription = proDescription;
        this.proCost = proCost;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getImageURLpro() {
        return imageURLpro;
    }

    public void setImageURLpro(String imageURLpro) {
        this.imageURLpro = imageURLpro;
    }

    public String getProDescription() {
        return proDescription;
    }

    public void setProDescription(String proDescription) {
        this.proDescription = proDescription;
    }

    public String getProCost() {
        return proCost;
    }

    public void setProCost(String proCost) {
        this.proCost = proCost;
    }
}
