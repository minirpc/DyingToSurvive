package com.dyingtosurvive.rpcdemointerface.model;

import java.io.Serializable;

/**
 * Created by change-solider on 18-9-18.
 */
public class User implements Serializable {
    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
