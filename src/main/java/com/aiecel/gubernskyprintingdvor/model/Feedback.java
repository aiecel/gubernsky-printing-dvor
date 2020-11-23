package com.aiecel.gubernskyprintingdvor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "feedbacks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Feedback {
    public static final int MAX_TO_STRING_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 5000)
    private String text;

    @PastOrPresent
    private ZonedDateTime sendingDateTime;

    @Override
    public String toString() {
        return text.length() > MAX_TO_STRING_LENGTH ? text.substring(0, MAX_TO_STRING_LENGTH) : text;
    }
}
