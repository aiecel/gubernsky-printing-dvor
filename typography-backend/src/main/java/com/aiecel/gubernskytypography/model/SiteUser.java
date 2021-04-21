package com.aiecel.gubernskytypography.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "site_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SiteUser extends User {
    private String username;
    private String encryptedPassword;
}
