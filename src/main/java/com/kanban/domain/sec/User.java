package com.kanban.domain.sec;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    private Long userId;

    @NotNull
    @Column(name = "username", unique = true)
    private String userName;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "")
    private String email;

    @Column(name = "enabled")
    private int enabled = 1;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "description")
    private String description;

    public User() {

    }

    public User(String userName, String password, String email, int enabled, String firstName, String lastName, String description) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public User(User user) {
        this.userId = user.userId;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.userName = user.userName;
        this.email = user.email;
        this.password = user.password;
        this.enabled = user.enabled;
    }

    public Boolean getEnabledBoolean() {
        if (enabled==1) {
            return true;
        } else if (enabled==0) {
            return false;
        } else {
            return null;
        }
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Long getUserid() {
        return userId;
    }

    public void setUserid(Long userid) {
        this.userId = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}