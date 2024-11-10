package com.ns.task.service.impl;

import com.ns.task.dto.*;
import com.ns.task.entity.ProductEntity;
import com.ns.task.entity.ProductReview;
import com.ns.task.mapper.ProductMapper;
import com.ns.task.mapper.ReviewMapper;
import com.ns.task.repository.ProductRepository;
import com.ns.task.service.IProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Response<ProductDetailDto> insertProduct(ProductEntity productEntity) {
        Response<ProductDetailDto> productInsertResponse = new Response<>();
        productInsertResponse.setData(new ProductDetailDto());

        productEntity.setCategory(productEntity.getCategory());
            productEntity.setTotalReviews(0L);
            productEntity.setAvgReview(0.0);
            ProductEntity save = productRepository.save(productEntity);
            ProductDetailDto productDetailDto = ProductMapper.INSTANCE.entityToProductDetailDto(save);
            productInsertResponse.setData(productDetailDto);
            productInsertResponse.setResponseDescription("product Inserted successful");

        return productInsertResponse;
    }

    public Response<ProductDetailDto> getProductById(int productId) {
        Response<ProductDetailDto> productResponse = new Response<>();

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();

            ProductDetailDto productDetailDto = ProductMapper.INSTANCE.entityToProductDetailDto(productEntity);

            List<ReviewDto> reviewList = new ArrayList<>();
            List<ProductReview> reviews = productEntity.getReview();

            if (reviews != null && !reviews.isEmpty()) {
                reviews.forEach(productReview -> {
                    ReviewDto reviewDto = ReviewMapper.INSTANCE.entityToDto(productReview);
                    reviewList.add(reviewDto);
                });
            }
            productDetailDto.setProductReviews(reviewList);

            productResponse.setData(productDetailDto);
            productResponse.setResponseDescription("Product found");
        } else {
            productResponse.setData(null);
            productResponse.setResponseDescription("No record found");
        }

        return productResponse;
    }


    public Page<ProductBannerDto> getAllProductDetails(int pageNo, int pageSize, String fieldName) {
        logger.info("Getting products pageNo:{} and pageSize:{}" ,pageNo, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        List<ProductBannerDto> productBannerDtosList = new ArrayList<>();

        Page<ProductEntity> productEntities = productRepository.findAll(pageRequest.withSort(Sort.Direction.ASC, fieldName));

        productEntities.forEach(productDetail -> {
                ProductBannerDto productBannerDto = ProductMapper.INSTANCE.entiryToproductDetailBannerDto(productDetail);
                productBannerDtosList.add(productBannerDto);
        });
        return new PageImpl<>(productBannerDtosList, pageRequest, productEntities.getTotalElements());
    }

    public Response<String> deleteProduct(int productId) {
        Response<String> deleteResponse = new Response<>();
        Optional<ProductEntity> productEntityOpt = productRepository.findById(productId);

        if (productEntityOpt.isPresent()) {
            productRepository.deleteById(productId);
            logger.info("Product image has been deleted from the file storage");
            deleteResponse.setResponseDescription("Deletion successful");
        } else {
            deleteResponse.setResponseDescription("No product found");
            logger.error("File deletion failed for path:{}");
        }
        return deleteResponse;
    }


    public Response<ProductEntity> updateProduct(ProductDetailDto productEntity) {
        Response<ProductEntity> updateResponse = new Response<>();

        Optional<ProductEntity> productPresent = productRepository.findById(productEntity.getProductId());
        if (productPresent.isPresent()) {
            ProductEntity updatedData = ProductMapper.INSTANCE.entityToDetailDto(productEntity, productPresent.get());
            updatedData.setCategory(productPresent.get().getCategory());

            ProductEntity save = productRepository.save(updatedData);

            updateResponse.setData(ProductMapper.INSTANCE.entityToEntity(save));

            updateResponse.setResponseDescription("Updated success");
        } else {
            updateResponse.setData(null);
            updateResponse.setResponseDescription("No product found by id");
        }

        return updateResponse;
    }

    @Override
    public Page<ProductBannerDto> search(int page, int pageSize, String search, String order) {
        logger.info("Searching product pageNo: {}, pageSize: {}", page, pageSize);

        Sort sort = order.equalsIgnoreCase("ASC") ? Sort.by("price").ascending() : Sort.by("price").descending();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);

        List<ProductBannerDto> searchProductResultList = new ArrayList<>();

        Page<ProductEntity> searchRequestResponse = productRepository.findBySearch(search, search, search, pageRequest);

        searchRequestResponse.forEach(product -> {
            ProductBannerDto productBannerDto = ProductMapper.INSTANCE.entiryToproductDetailBannerDto(product);
            searchProductResultList.add(productBannerDto);
        });

        return new PageImpl<>(searchProductResultList, pageRequest, searchRequestResponse.getTotalElements());
    }
}

