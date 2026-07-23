package com.peruviana.sale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_sale")
@DynamicInsert
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale")
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Relación One-to-Many con el detalle de la venta
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleDetailEntity> details;
}