package com.ns.task.service;

import com.ns.task.dto.ProductBannerDto;
import com.ns.task.dto.Response;
import com.ns.task.entity.ProductEntity;
import com.ns.task.dto.ProductDetailDto;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface IProductService {
    Response<ProductDetailDto> insertProduct(ProductEntity productEntity);
    Response<ProductDetailDto> getProductById(int productId) throws IOException;
    Page<ProductBannerDto>  getAllProductDetails(int pageNo, int pageSize, String fieldName);
    Response<String> deleteProduct(int productId);
    Response<ProductEntity> updateProduct(ProductDetailDto productEntity);
    Page<ProductBannerDto> search(int page, int pageSize, String search, String order);
}
