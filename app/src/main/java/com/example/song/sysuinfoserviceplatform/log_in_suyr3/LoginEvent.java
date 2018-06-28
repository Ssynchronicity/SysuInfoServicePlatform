package com.example.song.sysuinfoserviceplatform.log_in_suyr3;

public class LoginEvent {
    public String fragType;
    public String userName;
    public String userPassword;

    public LoginEvent(String type, String name, String psw) {
        fragType = type;
        userName = name;
        userPassword = psw;
    }
}
