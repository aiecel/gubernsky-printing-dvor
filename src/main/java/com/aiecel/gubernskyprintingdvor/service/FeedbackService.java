package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Feedback;

public interface FeedbackService extends DAOService<Feedback> {
    Feedback save(String feedbackText);
}
