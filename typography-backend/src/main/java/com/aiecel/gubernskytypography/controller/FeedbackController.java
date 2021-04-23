package com.aiecel.gubernskytypography.controller;

import com.aiecel.gubernskytypography.dto.FeedbackDTO;
import com.aiecel.gubernskytypography.dto.NewFeedbackDTO;
import com.aiecel.gubernskytypography.dto.mapping.FeedbackDTOMapper;
import com.aiecel.gubernskytypography.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/send")
    public FeedbackDTO sendFeedback(@RequestBody NewFeedbackDTO feedback) {
        return Mappers.getMapper(FeedbackDTOMapper.class).toDto(feedbackService.save(feedback));
    }
}
