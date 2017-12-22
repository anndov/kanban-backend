package com.kanban.model.request;

import java.io.Serializable;

/**
 * Created by david on 21-Dec-17.
 */
public class ProfileUpdateRequest implements Serializable {

    String firstName;
    String lastName;

    public ProfileUpdateRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
