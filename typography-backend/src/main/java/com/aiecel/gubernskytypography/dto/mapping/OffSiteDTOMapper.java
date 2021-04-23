package com.aiecel.gubernskytypography.dto.mapping;

import com.aiecel.gubernskytypography.dto.OffSiteUserDTO;
import com.aiecel.gubernskytypography.model.OffSiteUser;
import org.mapstruct.Mapper;

@Mapper
public interface OffSiteDTOMapper extends DTOMapper<OffSiteUser, OffSiteUserDTO> {
}
