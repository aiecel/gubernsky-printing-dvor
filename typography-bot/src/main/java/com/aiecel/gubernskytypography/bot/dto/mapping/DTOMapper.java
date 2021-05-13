package com.aiecel.gubernskytypography.bot.dto.mapping;

public interface DTOMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
