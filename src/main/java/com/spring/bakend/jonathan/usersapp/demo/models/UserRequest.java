package com.spring.bakend.jonathan.usersapp.demo.models;

import java.util.List;

import com.spring.bakend.jonathan.usersapp.demo.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRequest implements IUser {
    @NotBlank
    private String name;
    
    @NotBlank
    private String lastname;
    
    @NotEmpty
    @Email
    private String email;
    
    @NotBlank
    @Size(min=4, max = 12)
    private String username;
    
    private List<Role> admin;
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> isAdmin() {
        return admin;
    }

    public void setAdmin(List<Role> admin) {
        this.admin = (List<Role>) admin;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

   
}
