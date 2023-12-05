package com.git.backend.daeng_nyang_connect.user.role;

public enum Role {

    NewUser("NewUser"),

    User("User"),

    Admin("Admin");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
