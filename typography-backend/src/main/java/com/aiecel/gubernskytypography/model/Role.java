package com.aiecel.gubernskytypography.model;

public enum Role {
    USER, ADMIN;


    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
