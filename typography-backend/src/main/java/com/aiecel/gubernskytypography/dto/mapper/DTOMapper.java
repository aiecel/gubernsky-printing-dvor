package com.aiecel.gubernskytypography.dto.mapper;

import org.mapstruct.Context;

public interface DTOMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
