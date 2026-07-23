package com.peruviana.sale.mapper;

import com.peruviana.sale.dto.SaleDetailResponseDto;
import com.peruviana.sale.dto.SaleRequestDto;
import com.peruviana.sale.dto.SaleResponseDto;
import com.peruviana.sale.entity.SaleDetailEntity;
import com.peruviana.sale.entity.SaleEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleMapper {

    public SaleEntity toEntity(SaleRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new SaleEntity();
    }

    public SaleResponseDto toResponseDto(SaleEntity entity) {
        if (entity == null) {
            return null;
        }

        List<SaleDetailResponseDto> detailDtos = null;
        if (entity.getDetails() != null) {
            detailDtos = entity.getDetails().stream()
                    .map(this::toDetailResponseDto)
                    .toList();
        }

        return new SaleResponseDto(
                entity.getId(),
                entity.getDate(),
                entity.getTotal(),
                detailDtos
        );
    }

    public SaleDetailResponseDto toDetailResponseDto(SaleDetailEntity detail) {
        if (detail == null) {
            return null;
        }

        String productName = (detail.getProduct() != null) ? detail.getProduct().getName() : "Desconocido";

        return new SaleDetailResponseDto(
                detail.getId(),
                detail.getProduct() != null ? detail.getProduct().getId() : null,
                productName,
                detail.getQuantity(),
                detail.getUnitPrice(),
                detail.getSubTotal()
        );
    }
}