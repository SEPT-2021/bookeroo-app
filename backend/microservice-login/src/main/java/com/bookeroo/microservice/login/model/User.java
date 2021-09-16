package com.bookeroo.microservice.login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Entity
public class User {

    public enum Role {
        USER, ADMIN
    }

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
    private String roles;
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return id == user.id
                && enabled == user.enabled
                && username.equals(user.username)
                && password.equals(user.password)
                && firstName.equals(user.firstName)
                && lastName.equals(user.lastName)
                && roles.equals(user.roles)
                && createdAt.equals(user.createdAt)
                && updatedAt.equals(user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, roles, enabled, createdAt, updatedAt);
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
