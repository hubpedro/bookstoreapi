package com.hubpedro.bookstoreapi.dto;

import java.util.Set;

public class AuthResponse {
    private String token;
    private String name;
    private Set<String> roles; // Set de Strings com os NOMES das roles

    // Construtor
    public AuthResponse(String token, String name, Set<String> roles) {
        this.token = token;
        this.name = name;
        this.roles = roles;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}