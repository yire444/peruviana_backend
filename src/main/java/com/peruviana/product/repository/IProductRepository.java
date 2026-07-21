package com.peruviana.product.repository;

import com.peruviana.product.entity.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    //VALIDATE DATA FOR REGISTER PRODUCT
    boolean existsByName(String name);

    //VALIDATE DATA FOR UPDATE
    boolean existsByNameAndIdNot(String name, Long id);

    //QUERIES FOR PRODUCT
    @Query("""
        SELECT p
        FROM ProductEntity p
        WHERE
                (:name IS NULL OR p.name = :name)
                 AND(:price IS NULL OR p.price <= :price)
                 AND(:active IS NULL OR p.active = :active)
        """)
    List<ProductEntity> findByAllFilters(
            @Param("name") String name,
            @Param("price")BigDecimal price,
            @Param("active") Boolean active,
            Sort sort
            );
}
