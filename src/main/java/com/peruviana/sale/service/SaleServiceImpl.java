package com.peruviana.sale.service;

import com.peruviana.product.entity.ProductEntity;
import com.peruviana.product.repository.IProductRepository;
import com.peruviana.sale.dto.SaleDetailRequestDto;
import com.peruviana.sale.dto.SaleRequestDto;
import com.peruviana.sale.dto.SaleResponseDto;
import com.peruviana.sale.entity.SaleDetailEntity;
import com.peruviana.sale.entity.SaleEntity;
import com.peruviana.sale.mapper.SaleMapper;
import com.peruviana.sale.repository.ISaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements ISaleService {

    private final ISaleRepository saleRepository;
    private final IProductRepository productRepository;
    private final SaleMapper saleMapper;

    //C: REGISTER SALE
    @Override
    @Transactional
    public SaleResponseDto registerSale(SaleRequestDto dto) {

        //DATE
        SaleEntity sale = saleMapper.toEntity(dto);
        sale.setDate(LocalDateTime.now());

        BigDecimal totalSale = BigDecimal.ZERO;
        List<SaleDetailEntity> details = new ArrayList<>();

        for (SaleDetailRequestDto dtoDetail : dto.getDetails()) {

            //VERIFY PRODUCT
            ProductEntity product = productRepository
                    .findById(dtoDetail.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + dtoDetail.getProductId()));

            //VERIFY STOCK
            if (product.getStock() < dtoDetail.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName() +
                        " (Stock disponible: " + product.getStock() + ")");
            }

            product.setStock(product.getStock() - dtoDetail.getQuantity());
            productRepository.save(product);

            BigDecimal unitPrice = product.getPrice();
            BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(dtoDetail.getQuantity()));

            totalSale = totalSale.add(subTotal);

            SaleDetailEntity detail = new SaleDetailEntity();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(dtoDetail.getQuantity());
            detail.setUnitPrice(unitPrice);
            detail.setSubTotal(subTotal);

            details.add(detail);
        }

        sale.setDetails(details);
        sale.setTotal(totalSale);

        SaleEntity savedSale = saleRepository.save(sale);

        return saleMapper.toResponseDto(savedSale);
    }

    //SEARCH SALE FOR ID
    @Override
    @Transactional
    public SaleResponseDto findById(Long id) {
        SaleEntity sale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada con el ID: " + id));
        return saleMapper.toResponseDto(sale);
    }
}