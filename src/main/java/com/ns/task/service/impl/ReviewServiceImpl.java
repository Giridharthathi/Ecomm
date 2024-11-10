package com.ns.task.service.impl;

import com.ns.task.dto.Response;
import com.ns.task.dto.ReviewDto;
import com.ns.task.entity.ProductEntity;
import com.ns.task.entity.ProductReview;
import com.ns.task.entity.UserEntity;
import com.ns.task.mapper.ReviewMapper;
import com.ns.task.repository.ProductRepository;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.repository.ReviewRepository;
import com.ns.task.service.IReview;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements IReview {
    private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final RegistrationRepository registrationRepository;

    public boolean insertUserReview(ReviewDto review, int productId) {
        ProductEntity productData = productRepository.findById(productId).orElse(null);
        if (productData != null) {

            UserEntity user = new UserEntity();

            ProductReview productReview = ReviewMapper.INSTANCE.dtoToEntityMapper(review);

            user.setId(review.getUserId_Fk());
            productReview.setUserId_Fk(user);
            productReview.setReviewdate(LocalDate.now());
            productReview.setProductId_Fk(productData);

            productData.setTotalReviews(productData.getTotalReviews() + 1);

            productRepository.save(productData);
            reviewRepository.save(productReview);
            return true;
        } else {
            return false;
        }
    }
    public Response<ProductReview> updateReview(ProductReview updateReview, int productId) {
        Response<ProductReview> updateResponse = new Response<>();
        ProductReview previousReview = reviewRepository.findById(updateReview.getReviewId()).orElse(null);
        if (previousReview != null) {

            ProductReview updatedReview = ReviewMapper.INSTANCE.entityMapperForUpdate(previousReview, updateReview);
            updatedReview.setReviewdate(LocalDate.now());
            ProductReview newUpdatedReview = reviewRepository.save(updatedReview);
            updateResponse.setData(newUpdatedReview);
            updateResponse.setResponseDescription("Updated successful");
        } else {
            updateResponse.setResponseDescription("Updation failed no review found by review id");
        }
        return updateResponse;
    }

    public Response<Page<ReviewDto>> getReviewByProductId(int productId, int pageSize, int pageNo, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by("reviewdate").ascending() : Sort.by("reviewdate").descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Response<Page<ReviewDto>> productReviewResponse = new Response<>();
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        Optional<ProductEntity> productExistence = productRepository.findById(productId);
        if (productExistence.isPresent()) {
            Page<ProductReview> reviewByProductId = reviewRepository.findReviewByProductId(productId, pageRequest);

            if (reviewByProductId != null) {
                logger.info("get review by productId pageSize:{}  pageNo:{} ", pageSize,pageNo);
                reviewByProductId.forEach(productReview -> {
                    ReviewDto reviewDto = ReviewMapper.INSTANCE.entityToDto(productReview);
                    reviewDtoList.add(reviewDto);
                });
                PageImpl<ReviewDto> reviewPageData = new PageImpl<>(reviewDtoList, pageRequest, reviewByProductId.getTotalElements());
                productReviewResponse.setData(reviewPageData);
                productReviewResponse.setResponseDescription("Reviews Found");
            } else {
                logger.info("Review are empty pageNo:{}", pageNo);
                productReviewResponse.setResponseDescription("Reviews are empty");
            }
        }else{
            logger.info("No product found for productId {}", productId);
            productReviewResponse.setResponseDescription("No product found by productId:-"+ productId);
        }
        return productReviewResponse;
    }

    public Response<ReviewDto> getReviewById(int reviewId) {
        Response<ReviewDto> reviewResponse = new Response<>();

        Optional<ProductReview> productReviewById = reviewRepository.findById(reviewId);
        if (productReviewById.isPresent()) {

            ReviewDto reviewDto = ReviewMapper.INSTANCE.entityToDto(productReviewById.get());

            reviewResponse.setData(reviewDto);
            reviewResponse.setResponseDescription("Review found");
        } else {
            reviewResponse.setData(null);
            reviewResponse.setResponseDescription("No review found by review id");
        }
        return reviewResponse;
    }

    public void updateAvgReview() {
        List<ProductEntity> allProducts = productRepository.findAll();
        allProducts.forEach(product -> {
            BigDecimal totalRating = BigDecimal.ZERO;
            int productId = product.getProductId();

            List<ProductReview> reviewsByProductId = reviewRepository.findReviewByProduct(productId);
            for (ProductReview reviewRating : reviewsByProductId) {
                totalRating = totalRating.add(BigDecimal.valueOf(reviewRating.getRating()));
            }

            Long totalReviews = product.getTotalReviews();
            if (totalReviews != null && totalReviews > 0) {
                BigDecimal avgReview = totalRating.divide(BigDecimal.valueOf(totalReviews), 2);
                product.setAvgReview(avgReview.doubleValue());
            } else {
                product.setAvgReview(0.0);
            }

            productRepository.save(product);
        });
    }

    public Response<Page<ProductReview>> getReviewsOfUserByUserId(int userId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Response<Page<ProductReview>> userReviewResponse = new Response<>();
        Optional<UserEntity> userExistence = registrationRepository.findById(userId);
        if (userExistence.isPresent()) {
            Page<ProductReview> reviewsForUserByUserId = reviewRepository.findReviewsForUserByUserId(userId, pageRequest);
            userReviewResponse.setData(reviewsForUserByUserId);
            userReviewResponse.setResponseDescription("Reviews found");
        } else {
            userReviewResponse.setResponseDescription("No reviews found by user id");
        }
        return userReviewResponse;
    }
}