package com.peruviana.sale.controller;

import com.peruviana.sale.dto.SaleRequestDto;
import com.peruviana.sale.dto.SaleResponseDto;
import com.peruviana.sale.service.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SaleController {

    private final ISaleService saleService;

    //REGISTER SALE /api/v1/sales
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> registerSale(@Valid @RequestBody SaleRequestDto dto) {
        SaleResponseDto response = saleService.registerSale(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
    }

    // R: GET /api/v1/sales/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> findById(@PathVariable Long id) {
        SaleResponseDto response = saleService.findById(id);
        return ResponseEntity.ok(response); // 200 OK
    }
}
