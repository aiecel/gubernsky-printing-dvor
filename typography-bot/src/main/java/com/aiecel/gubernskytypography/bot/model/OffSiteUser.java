package com.aiecel.gubernskytypography.bot.model;

import com.aiecel.gubernskytypography.bot.api.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OffSiteUser implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String displayName;
    private String provider;

    @Override
    public String toString() {
        return displayName;
    }
}
