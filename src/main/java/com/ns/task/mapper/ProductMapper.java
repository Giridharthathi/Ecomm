package com.ns.task.mapper;

import com.ns.task.dto.ProductBannerDto;
import com.ns.task.dto.ProductDetailDto;
import com.ns.task.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "brand",target = "brandName")
    ProductDetailDto entityToProductDetailDto(ProductEntity entity);

    ProductBannerDto entiryToproductDetailBannerDto(ProductEntity entity);

    @Mapping(target = "name",source = "productDetailDto.name")
    @Mapping(target = "brand",source = "productDetailDto.brandName")
    @Mapping(target = "category",source = "productEntity.category")
    @Mapping(target = "review",ignore = true)
    @Mapping(target = "totalReviews",source = "productEntity.totalReviews")
    @Mapping(target = "avgReview",source = "productEntity.avgReview")
    @Mapping(target = "productId",source = "productEntity.productId")
    @Mapping(target = "stock",source = "productEntity.stock")
    @Mapping(target = "description",source = "productEntity.description")
    @Mapping(target = "price",source = "productEntity.price")
    ProductEntity entityToDetailDto(ProductDetailDto productDetailDto,ProductEntity productEntity);

    ProductEntity entityToEntity(ProductEntity entity);
    ProductEntity dtoToEntity(ProductDetailDto dto);
}
