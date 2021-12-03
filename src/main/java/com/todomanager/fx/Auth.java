package com.todomanager.fx;

public class Auth {
    private static Auth auth;
    private String login;

    private Auth() {}

    public static synchronized Auth getInstance() {
        if(auth == null) auth = new Auth();
        return auth;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
