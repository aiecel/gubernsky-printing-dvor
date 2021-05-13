package com.aiecel.gubernskytypography.bot.dto.mapping;

import com.aiecel.gubernskytypography.bot.dto.ProductDTO;
import com.aiecel.gubernskytypography.bot.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductDTOMapper extends DTOMapper<Product, ProductDTO> {
    @Override
    @Mapping(target = "externalId", source = "id")
    Product toEntity(ProductDTO dto);

    @Override
    @Mapping(target = "id", source = "externalId")
    ProductDTO toDto(Product entity);
}
