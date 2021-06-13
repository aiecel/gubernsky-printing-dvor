package com.aiecel.gubernskytypography.dto.mapping;

import com.aiecel.gubernskytypography.dto.UserDTO;
import com.aiecel.gubernskytypography.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserDTOMapper {
    UserDTO toDto(User entity);
}
