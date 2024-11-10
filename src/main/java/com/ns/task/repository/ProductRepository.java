package com.ns.task.repository;

import com.ns.task.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
    @Query(value = "SELECT * FROM product WHERE brand=? OR category=? OR name=?",nativeQuery = true)
    Page<ProductEntity> findBySearch(String brand,String category,String name,Pageable pageable);
}
