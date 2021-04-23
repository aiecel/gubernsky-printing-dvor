package com.aiecel.gubernskytypography.dto.mapping;

import com.aiecel.gubernskytypography.dto.FeedbackDTO;
import com.aiecel.gubernskytypography.model.Feedback;
import org.mapstruct.Mapper;

@Mapper
public interface FeedbackDTOMapper extends DTOMapper<Feedback, FeedbackDTO> {
}
