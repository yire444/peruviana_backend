package com.peruviana.product.service;

import com.peruviana.product.dto.ProductFilterDto;
import com.peruviana.product.dto.ProductRequestDto;
import com.peruviana.product.dto.ProductResponseDto;
import com.peruviana.product.entity.ProductEntity;
import com.peruviana.product.mapper.ProductMapper;
import com.peruviana.product.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository repoProduct;
    private final ProductMapper mapProduct;

    // C: REGISTER PRODUCT
    @Override
    @Transactional
    public ProductResponseDto registerProduct(ProductRequestDto dto) {
        if (repoProduct.existsByName(dto.getName())) {
            throw new IllegalArgumentException("El producto ya se encuentra registrado con el nombre: " + dto.getName());
        }

        ProductEntity entity = mapProduct.toEntity(dto);
        ProductEntity savedProduct = repoProduct.save(entity);

        return mapProduct.toResponseDto(savedProduct);
    }

    // SEARCH PRODUCT BY ID
    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        ProductEntity entity = repoProduct.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con el ID: " + id));
        return mapProduct.toResponseDto(entity);
    }

    // LIST ALL PRODUCTS AND FILTERS
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> lstProductAndFilter(ProductFilterDto dto) {
        List<ProductEntity> entities = repoProduct.findByAllFilters(
                dto.getName(),
                dto.getPrice(),
                dto.getActive(),
                dto.getSort()
        );

        return entities.stream()
                .map(mapProduct::toResponseDto)
                .toList();
    }

    // U: UPDATE PRODUCT
    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        ProductEntity entity = repoProduct.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede actualizar. Producto no encontrado con ID: " + id));

        if (repoProduct.existsByNameAndIdNot(dto.getName(), id)) {
            throw new IllegalArgumentException("Ya existe otro producto registrado con el nombre: " + dto.getName());
        }

        mapProduct.updateEntityFromDto(dto, entity);
        ProductEntity updateEntity = repoProduct.save(entity);

        return mapProduct.toResponseDto(updateEntity);
    }

    // D: DELETE PRODUCT
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity entity = repoProduct.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede eliminar. Producto no encontrado con ID: " + id));

        entity.setActive(false);
        repoProduct.save(entity);
    }
}