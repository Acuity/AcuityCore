package com.acuity.db.domain.common.message_data;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class LoginData {

    private String email;
    private int sessionType;
    private String password;

    public LoginData(String email, String password, int sessionType) {
        this.email = email;
        this.sessionType = sessionType;
        this.password = password;
    }

    public LoginData() {
    }

    public String getEmail() {
        return email;
    }

    public int getSessionType() {
        return sessionType;
    }

    public String getPassword() {
        return password;
    }
}
