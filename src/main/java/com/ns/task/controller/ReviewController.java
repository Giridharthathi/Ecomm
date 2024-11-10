package com.ns.task.controller;

import com.ns.task.dto.Response;
import com.ns.task.dto.ReviewDto;
import com.ns.task.entity.ProductReview;
import com.ns.task.service.IReview;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("api/review")
@AllArgsConstructor
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final IReview reviewService;

    @PostMapping("/insert")
    private ResponseEntity<String> insertReview(@RequestParam(name = "productId") int productId,
                                                @RequestBody ReviewDto userReview) {
        logger.info("Insert new Review");
        boolean reviewConfirmation = reviewService.insertUserReview(userReview, productId);
        if (reviewConfirmation) {
            logger.info("inserted successful");
            return new ResponseEntity<>("Inserted SuccessFully", HttpStatus.OK);
        } else {
            logger.info("Insertion failed");
            return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    private ResponseEntity<Response<ProductReview>> updateReview(@RequestParam("prodcutId") int productId,
                                                @RequestParam("reviewId") int reviewId,
                                                @RequestBody ProductReview updatedReview) {
        logger.info("update review");
        Response<ProductReview> updateResponse = reviewService.updateReview(updatedReview, productId);
        if (updateResponse.getData()!=null) {
            logger.info("update successful");
            return new ResponseEntity<>(updateResponse, HttpStatus.OK);
        } else {
            logger.info("updation failed");
            return new ResponseEntity<>(updateResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{productId}")
    private ResponseEntity<Response<Page<ReviewDto>>> getReviewByProductId(@PathVariable("productId") int id,
                                                                 @RequestParam(name = "pageSize") int pageSize,
                                                                 @RequestParam(name = "pageNo") int pageNo,
                                                                 @RequestParam(name = "sort", defaultValue = "ASC") String sortOrder) {
        logger.info("Getting reviews based on productId");
        Response<Page<ReviewDto>> reviewByProductId = reviewService.getReviewByProductId(id, pageSize, pageNo, sortOrder);
        return new ResponseEntity<>(reviewByProductId, HttpStatus.OK);
    }

    @GetMapping("/getReview/{reviewId}")
    private ResponseEntity<Response<ReviewDto>> getReviewByReviewId(@PathVariable("reviewId") int reviewId) {
        logger.info("Get review by review ID");
        Response<ReviewDto> reviewById = reviewService.getReviewById(reviewId);
        if (reviewById.getData() != null) {
            logger.info(reviewById.getResponseDescription());
            return new ResponseEntity<>(reviewById, HttpStatus.OK);
        } else {
            logger.info(reviewById.getResponseDescription());
            return new ResponseEntity<>(reviewById, HttpStatus.OK);
        }
    }

    @GetMapping("user/get-related")
    private ResponseEntity<Response<Page<ProductReview>>> getUserRelatedReviewsByUserId(@RequestParam("userId") int userId,
                                                                        @RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
                                                                        @RequestParam(value = "pageSize",defaultValue = "3") int pageSize){
        Response<Page<ProductReview>> reviewsOfUserByUserId = reviewService.getReviewsOfUserByUserId(userId, pageNo, pageSize);
        return new ResponseEntity<>(reviewsOfUserByUserId, HttpStatus.OK);
    }

    @Scheduled(fixedRate = 300000)
    private void autoUpdateReviewRating() {
        logger.info("Updating the reviews for every 5MIN[ {} ] [ {} ]",new Date().getTime(), LocalDate.now());
        reviewService.updateAvgReview();
    }
}
