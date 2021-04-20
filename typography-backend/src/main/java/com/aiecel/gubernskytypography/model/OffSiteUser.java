package com.aiecel.gubernskytypography.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "offsite_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OffSiteUser extends User {
    private String username;
    private String displayName;
    private String registration;

    @Override
    public String toString() {
        return username.equals(displayName) ? username : displayName + " (" + username + ")";
    }
}
