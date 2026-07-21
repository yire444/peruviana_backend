package com.peruviana.product.service;

import com.peruviana.product.dto.ProductFilterDto;
import com.peruviana.product.dto.ProductRequestDto;
import com.peruviana.product.dto.ProductResponseDto;

import java.util.List;

public interface IProductService {

    //C: REGISTER PRODUCT
    ProductResponseDto registerProduct(ProductRequestDto dto);

    //R: SEARCH PRODUCT POR ID
    ProductResponseDto findById(Long id);

    //R: LIST ALL PRODUCTS AND FILTERS
    List<ProductResponseDto> lstProductAndFilter(ProductFilterDto dto);

    //U: UPDATE PRODUCT
    ProductResponseDto updateProduct(Long id, ProductRequestDto dto);

    //D: DELETE PRODUCT
    void deleteProduct(Long id);
}
