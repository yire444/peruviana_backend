package com.peruviana.sale.repository;

import com.peruviana.sale.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepository extends JpaRepository<SaleEntity, Long> {
}
