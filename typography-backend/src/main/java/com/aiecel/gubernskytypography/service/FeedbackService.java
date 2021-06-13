package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.dto.NewFeedbackDTO;
import com.aiecel.gubernskytypography.model.Feedback;

public interface FeedbackService extends DAOService<Feedback> {
    Feedback save(NewFeedbackDTO feedback);
}
