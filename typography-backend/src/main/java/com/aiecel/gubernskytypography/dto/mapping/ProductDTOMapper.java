package com.aiecel.gubernskytypography.dto.mapping;

import com.aiecel.gubernskytypography.dto.ProductDTO;
import com.aiecel.gubernskytypography.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductDTOMapper extends DTOMapper<Product, ProductDTO> {
}
