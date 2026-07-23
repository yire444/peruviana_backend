package com.peruviana.sale.service;

import com.peruviana.sale.dto.SaleRequestDto;
import com.peruviana.sale.dto.SaleResponseDto;

public interface ISaleService {

    SaleResponseDto registerSale(SaleRequestDto dto);

    SaleResponseDto findById(Long id);
}
