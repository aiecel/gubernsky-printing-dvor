package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.dto.OffSiteUserDTO;
import com.aiecel.gubernskytypography.dto.mapping.OffSiteUserDTOMapper;
import com.aiecel.gubernskytypography.model.OffSiteUser;
import org.mapstruct.factory.Mappers;

import javax.validation.Valid;

public interface OffSiteUserService {
    boolean exists(OffSiteUser user);

    OffSiteUser register(OffSiteUser user);

    default OffSiteUserDTO register(@Valid OffSiteUserDTO userDTO) {
        return Mappers.getMapper(OffSiteUserDTOMapper.class)
                .toDto(register(Mappers.getMapper(OffSiteUserDTOMapper.class).toEntity(userDTO)));
    }
}
