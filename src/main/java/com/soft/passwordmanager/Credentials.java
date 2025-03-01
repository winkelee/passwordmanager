package com.soft.passwordmanager;

public class Credentials {

    String username;
    String password;
    String hostUrl;

    public Credentials(String username, String password, String hostUrl) {
        this.username = username;
        this.password = password;
        this.hostUrl = hostUrl;
    }

    public Credentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
