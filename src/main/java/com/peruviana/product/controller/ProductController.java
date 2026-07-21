package com.peruviana.product.controller;

import com.peruviana.product.dto.ProductFilterDto;
import com.peruviana.product.dto.ProductRequestDto;
import com.peruviana.product.dto.ProductResponseDto;
import com.peruviana.product.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final IProductService serviceProduct;

    // C: POST /api/v1/products
    @PostMapping
    public ResponseEntity<ProductResponseDto> registerProduct(@Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto response = serviceProduct.registerProduct(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
    }

    // R: GET /api/v1/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id) {
        ProductResponseDto response = serviceProduct.findById(id);
        return ResponseEntity.ok(response); // 200 OK
    }

    // R: GET /api/v1/products
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> lstProductAndFilter(@ModelAttribute ProductFilterDto dto) {
        List<ProductResponseDto> response = serviceProduct.lstProductAndFilter(dto);
        return ResponseEntity.ok(response); // 200 OK
    }

    // U: PUT /api/v1/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto response = serviceProduct.updateProduct(id, dto);
        return ResponseEntity.ok(response); // 200 OK
    }

    // D: DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        serviceProduct.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
