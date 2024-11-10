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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReviewServiceImplTest {

  @Autowired
    private IReview review;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private RegistrationRepository registrationRepository;
    private ProductReview reviewProduct;
    private ProductEntity productEntity;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        productEntity = new ProductEntity(1,"MOCK","MOCK_BRAND",0,"DESCRIPTION",
                10,"MOCK_CATEGORY", null,
                0L,0.0);
        reviewProduct = new ProductReview(2,4,"Wonderful",
                LocalDate.now(),productEntity,new UserEntity());
        review = new ReviewServiceImpl(reviewRepository,productRepository,registrationRepository);
    }

    @Test
    void insertProductReview(){
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(productEntity));
        when(reviewRepository.save(any(ProductReview.class))).thenReturn(reviewProduct);

        productEntity.setTotalReviews(productEntity.getTotalReviews()+1);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ReviewDto reviewDto = ReviewMapper.INSTANCE.entityToDto(reviewProduct);

        boolean insertConfirmation = review.insertUserReview(reviewDto, any(Integer.class));
        assertTrue(insertConfirmation);
    }

    @Test
    void insertProductReview_WhenProductNotFound(){
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(reviewRepository.save(any(ProductReview.class))).thenReturn(reviewProduct);

        productEntity.setTotalReviews(productEntity.getTotalReviews()+1);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ReviewDto reviewDto = ReviewMapper.INSTANCE.entityToDto(reviewProduct);

        boolean insertConfirmation = review.insertUserReview(reviewDto, any(Integer.class));
        assertFalse(insertConfirmation);
    }

    @Test
    void getReviewById(){
        when(reviewRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(reviewProduct));
        Response<ReviewDto> reviewById = review.getReviewById(any(Integer.class));
        assertNotNull(reviewById.getData());
    }

    @Test
    void getReviewById_WhenProductNotFound(){
        when(reviewRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Response<ReviewDto> reviewById = review.getReviewById(any(Integer.class));
        assertNull(reviewById.getData());
    }


    @Test
    void getReviewsByUserId(){
        Page<ProductReview> expectedReviews = new PageImpl<>(Collections.singletonList(reviewProduct));
        UserEntity userEntity = new UserEntity(1,"Giri", "giridhar@gmail.com","123456",955141929L,null,null,null);

        when(reviewRepository.findReviewsForUserByUserId(any(Integer.class),any(Pageable.class))).thenReturn(expectedReviews);
        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.of(userEntity));

        Response<Page<ProductReview>> reviewsOfUserByUserId = review.getReviewsOfUserByUserId(any(Integer.class),0, 10);
        assertNotNull(reviewsOfUserByUserId.getData());
    }

    @Test
    void getReviewsByUserId_WhenUserNotFound(){
        Page<ProductReview> expectedReviews = new PageImpl<>(Collections.singletonList(reviewProduct));

        when(reviewRepository.findReviewsForUserByUserId(any(Integer.class),any(Pageable.class))).thenReturn(expectedReviews);
        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Response<Page<ProductReview>> reviewsOfUserByUserId = review.getReviewsOfUserByUserId(any(Integer.class),0, 10);
        assertNull(reviewsOfUserByUserId.getData());
    }

    @Test
    void getReviewForProduct(){
        Page<ProductReview> expectedReviews = new PageImpl<>(Arrays.asList(reviewProduct,reviewProduct,reviewProduct));
        when(reviewRepository.findReviewByProductId(any(Integer.class),any(Pageable.class))).thenReturn(expectedReviews);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(productEntity));
        Response<Page<ReviewDto>> reviewByProductIdResponse = review.getReviewByProductId(1,10, 0, "ASC");
        assertNotNull(reviewByProductIdResponse.getData());
    }
    @Test
    void getReviewForProduct_WhenProductFound_ReviewsNotFound(){
        when(reviewRepository.findReviewByProductId(any(Integer.class),any(Pageable.class))).thenReturn(null);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(productEntity));
        Response<Page<ReviewDto>> reviewByProductIdResponse = review.getReviewByProductId(1,10, 0, "ASC");
        assertNull(reviewByProductIdResponse.getData());
    }
    @Test
    void getReviewForProduct_WhenProductNotFound(){
        Page<ProductReview> expectedReviews = new PageImpl<>(Arrays.asList(reviewProduct,reviewProduct,reviewProduct));
        when(reviewRepository.findReviewByProductId(any(Integer.class),any(Pageable.class))).thenReturn(expectedReviews);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Response<Page<ReviewDto>> reviewByProductIdResponse = review.getReviewByProductId(1,10, 0, "ASC");
        assertNull(reviewByProductIdResponse.getData());
    }

    @Test
    void updateReview(){
        ProductReview updatedReview = new ProductReview(2,4,"Wonderful",
                LocalDate.now(),productEntity,new UserEntity());
        when(reviewRepository.findById(any(Integer.class))).thenReturn(Optional.of(reviewProduct));
        when(reviewRepository.save(any(ProductReview.class))).thenReturn(updatedReview);
        Response<ProductReview> updateResponse = review.updateReview(reviewProduct, 1);
        assertNotNull(updateResponse.getData());
    }

    @Test
    void updateReview_whenProductNotFound(){
        ProductReview updatedReview = new ProductReview(2,4,"Wonderful",
                LocalDate.now(),productEntity,new UserEntity());
        when(reviewRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(reviewRepository.save(any(ProductReview.class))).thenReturn(updatedReview);
        Response<ProductReview> updateResponse = review.updateReview(reviewProduct, 1);
        assertNull(updateResponse.getData());
    }
}