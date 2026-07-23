package com.peruviana.product.mapper;

import com.peruviana.product.dto.ProductRequestDto;
import com.peruviana.product.dto.ProductResponseDto;
import com.peruviana.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(ProductRequestDto dto) {
        if(dto == null) { return null;}

        ProductEntity entity = new ProductEntity();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setActive(true);

        return entity;
    }

    public ProductResponseDto toResponseDto(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ProductResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getActive()
        );
    }

    public void updateEntityFromDto(ProductRequestDto dto, ProductEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
    }
}
