package com.hubpedro.bookstoreapi.dto;

public record LoginRequest(String name, String password) {
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
