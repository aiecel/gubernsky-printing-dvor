package com.aiecel.gubernskytypography.dto.mapping;

public interface DTOMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
