package com.aiecel.gubernskytypography.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vk_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VkUser extends User {
    @Column(name = "vk_id")
    private int vkId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName + ", vk id = " + vkId;
    }
}
