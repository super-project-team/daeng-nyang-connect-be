package com.git.backend.daeng_nyang_connect.user.role;

public enum Role {


    User("ROLE_User"),

    Admin("ROLE_Admin");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
