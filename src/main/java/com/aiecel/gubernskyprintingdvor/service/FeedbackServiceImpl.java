package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Feedback;
import com.aiecel.gubernskyprintingdvor.repository.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void save(String feedbackText) {
        Feedback feedback = new Feedback();
        feedback.setText(feedbackText);
        feedback.setSendingDateTime(ZonedDateTime.now());

        feedbackRepository.save(feedback);
        log.info("New feedback - {}",
                feedback.getText().length() <= 50 ? feedback.getText() : feedback.getText().substring(0, 50) + "..."
        );
    }
}
