package com.ttl.ritz7;

public class modelClient {
    public String cliName;
    public String imageURLcli;
    public  String cliDescription;
    public  String cliDate;

    public modelClient() {
    }

    public modelClient(String cliName, String imageURLcli, String cliDescription, String cliDate) {
        this.cliName = cliName;
        this.imageURLcli = imageURLcli;
        this.cliDescription = cliDescription;
        this.cliDate = cliDate;
    }

    public String getCliName() {
        return cliName;
    }

    public void setCliName(String cliName) {
        this.cliName = cliName;
    }

    public String getImageURLcli() {
        return imageURLcli;
    }

    public void setImageURLcli(String imageURLcli) {
        this.imageURLcli = imageURLcli;
    }

    public String getCliDescription() {
        return cliDescription;
    }

    public void setCliDescription(String cliDescription) {
        this.cliDescription = cliDescription;
    }

    public String getCliDate() {
        return cliDate;
    }

    public void setCliDate(String cliDate) {
        this.cliDate = cliDate;
    }
}
