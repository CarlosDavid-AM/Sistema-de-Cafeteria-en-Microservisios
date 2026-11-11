package com.cafeteria.productos.repository;

import com.cafeteria.productos.entity.Products;
import com.cafeteria.productos.enums.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {

    Page<Products> findByCategory(CategoryEnum category, Pageable pageable);

    @Query("""
        SELECT p FROM Products p
        WHERE LOWER(p.product_name) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    Page<Products> searchByProductName(
            @Param("name") String name,
            Pageable pageable
    );


}
