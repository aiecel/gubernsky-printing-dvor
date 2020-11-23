package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Feedback;
import com.aiecel.gubernskyprintingdvor.repository.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback save(String feedbackText) {
        Feedback feedback = new Feedback();
        feedback.setText(feedbackText);
        feedback.setSendingDateTime(ZonedDateTime.now());
        return save(feedback);
    }

    @Override
    public Feedback save(Feedback feedback) {
        Feedback savedFeedback = feedbackRepository.save(feedback);
        log.info("New feedback! - {}", feedback);
        return savedFeedback;
    }
}
