package com.bookeroo.microservice.admin.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;

/**
 * User JPA entity to represent the user data model.
 */
@Entity
public class User {

    public enum UserRole {
        USER("ROLE_USER"),
        ADMIN("ROLE_ADMIN"),
        SELLER("ROLE_SELLER");

        public String name;

        UserRole(String name) {
            this.name = name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    @JsonManagedReference(value = "User_SellerDetails")
    private SellerDetails sellerDetails;
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
    @NotBlank(message = "Address line 1 cannot be blank")
    private String addressLine1;
    private String addressLine2;
    @NotBlank(message = "City cannot be blank")
    private String city;
    @NotBlank(message = "State cannot be blank")
    private String state;
    @NotBlank(message = "Postal code cannot be blank")
    @Pattern(regexp = "(^[0-9]{4}$)", message = "Not a valid postal code")
    private String postalCode;
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "(^(\\+?\\(61\\)|\\(\\+?61\\)|\\+?61|\\(0[1-9]\\)|0[1-9])?( ?-?[0-9]){7,9}$)", message = "Not a valid phone number")
    private String phoneNumber;
    @NotBlank(message = "User needs to have a role defined")
    @Pattern(regexp = "(^ROLE_USER|ROLE_SELLER|ROLE_ADMIN)", message = "Not a valid role")
    private String role;
    @NotNull(message = "Boolean flag enable has to be set")
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

    public SellerDetails getSellerDetails() {
        return sellerDetails;
    }

    public void setSellerDetails(SellerDetails sellerDetails) {
        this.sellerDetails = sellerDetails;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        User user = (User) object;

        if (id != user.id) return false;
        if (enabled != user.enabled) return false;
        if (!Objects.equals(sellerDetails, user.sellerDetails)) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!addressLine1.equals(user.addressLine1)) return false;
        if (!addressLine2.equals(user.addressLine2)) return false;
        if (!city.equals(user.city)) return false;
        if (!state.equals(user.state)) return false;
        if (!postalCode.equals(user.postalCode)) return false;
        if (!phoneNumber.equals(user.phoneNumber)) return false;
        return role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sellerDetails != null ? sellerDetails.hashCode() : 0);
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + addressLine1.hashCode();
        result = 31 * result + addressLine2.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + postalCode.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
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
                id, username, password, firstName, lastName, role, enabled, createdAt, updatedAt);
    }

}
