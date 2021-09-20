package com.bookeroo.microservice.login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User JPA entity to represent the user data model.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Username cannot be blank")
    @Email(message = "Username needs to be an email address")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "Firstname cannot be blank")
    private String firstName;
    @NotBlank(message = "Lastname cannot be blank")
    private String lastName;
    @NotBlank(message = "User needs to have one or more roles of format ROLE_{label}")
    private String roles;
    @NotNull(message = "Boolean flag enable has to be set")
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;

    public enum Role {
        USER, ADMIN
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @Override
    @JsonIgnore
    public String toString() {
        return String.format("User {\n" +
                        "\tid: %s,\n" +
                        "\tusername: %s\n" +
                        "\tpassword: %s\n" +
                        "\tfirstName: %s\n" +
                        "\tlastName: %s\n" +
                        "\troles: %s\n" +
                        "\tenabled: %b\n" +
                        "\tcreatedAt: %s\n" +
                        "\tupdatedAt: %s\n" +
                        "}",
                id, username, password, firstName, lastName, roles, enabled, createdAt, updatedAt);
    }

}
