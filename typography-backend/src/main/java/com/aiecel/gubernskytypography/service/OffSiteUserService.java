package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.dto.OffSiteUserDTO;
import com.aiecel.gubernskytypography.dto.mapping.OffSiteDTOMapper;
import com.aiecel.gubernskytypography.model.OffSiteUser;
import org.mapstruct.factory.Mappers;

import javax.validation.Valid;

public interface OffSiteUserService {
    boolean exists(OffSiteUser user);

    OffSiteUser register(OffSiteUser user);

    default OffSiteUserDTO register(@Valid OffSiteUserDTO userDTO) {
        return Mappers.getMapper(OffSiteDTOMapper.class)
                .toDto(register(Mappers.getMapper(OffSiteDTOMapper.class).toEntity(userDTO)));
    }
}
