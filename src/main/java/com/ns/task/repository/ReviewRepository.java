package com.ns.task.repository;

import com.ns.task.entity.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ProductReview, Integer> {
    @Query(value = "SELECT * FROM review WHERE product_id_fk =?",nativeQuery = true)
    Page<ProductReview> findReviewByProductId(int productId, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE product_id_fk =?",nativeQuery = true)
    List<ProductReview> findReviewByProduct(int productId);

    @Query(value = "SELECT * FROM review WHERE user_id_fk =?",nativeQuery = true)
    Page<ProductReview> findReviewsForUserByUserId(int userId, Pageable pageable);

}
