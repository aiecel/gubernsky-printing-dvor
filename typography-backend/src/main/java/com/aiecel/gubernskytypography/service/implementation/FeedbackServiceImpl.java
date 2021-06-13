package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.dto.NewFeedbackDTO;
import com.aiecel.gubernskytypography.model.Feedback;
import com.aiecel.gubernskytypography.repository.FeedbackRepository;
import com.aiecel.gubernskytypography.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Override
    public Feedback save(NewFeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setText(feedbackDTO.getText());
        feedback.setSendingDateTime(ZonedDateTime.now());
        return save(feedback);
    }

    @Override
    public Feedback save(Feedback entity) {
        Feedback savedFeedback = feedbackRepository.save(entity);
        log.info("New feedback! - {}", savedFeedback);
        return savedFeedback;
    }
}
