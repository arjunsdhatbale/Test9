package com.main.Test9.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UserMasterDto {

    @NotEmpty(message = "User name Required")
    private String userName ;
    @NotEmpty(message = "Password is Required")
    private String password;

    public UserMasterDto(){
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword(){
        return  password;
    }
    public  void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserMasterDto{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
