package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.dto.NewFeedbackDTO;
import com.aiecel.gubernskytypography.model.Feedback;

import javax.validation.Valid;

public interface FeedbackService extends DAOService<Feedback> {
    Feedback save(@Valid NewFeedbackDTO feedback);
}
