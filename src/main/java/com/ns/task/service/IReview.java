package com.ns.task.service;

import com.ns.task.dto.Response;
import com.ns.task.entity.ProductReview;
import com.ns.task.dto.ReviewDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IReview {
    boolean insertUserReview(ReviewDto review, int productId);
    Response<ProductReview> updateReview(ProductReview updateReview, int productId);
    Response<Page<ReviewDto>> getReviewByProductId(int productId, int pageSize, int pageNo, String sortOrder);
    Response<ReviewDto> getReviewById(int reviewId);
    void updateAvgReview();
    Response<Page<ProductReview>> getReviewsOfUserByUserId(int userId, int pageNo, int pageSize);
}
