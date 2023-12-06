package com.git.backend.daeng_nyang_connect.user.role;

public enum Role {


    USER("ROLE_User"),

    ADMIN("ROLE_Admin");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
