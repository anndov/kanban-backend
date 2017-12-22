package com.kanban.model.request;

import java.io.Serializable;

/**
 * Created by david on 21-Dec-17.
 */
public class PasswordChangeRequest implements Serializable {

    String oldPassword;
    String newPassword;

    public PasswordChangeRequest() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
