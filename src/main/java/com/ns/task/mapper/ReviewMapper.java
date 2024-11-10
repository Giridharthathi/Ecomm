package com.ns.task.mapper;

import com.ns.task.dto.ReviewDto;
import com.ns.task.entity.ProductReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "productId_Fk", source = "entity.productId_Fk.productId")
    @Mapping(target = "userId_Fk", source = "entity.userId_Fk.id")
    ReviewDto entityToDto(ProductReview entity);

    @Mapping(target = "reviewdate", source = "updateReview.reviewdate")
    @Mapping(target = "rating", source = "updateReview.rating")
    @Mapping(target = "reviewDescription", source = "updateReview.reviewDescription")
    @Mapping(target = "productId_Fk", source = "originalReview.productId_Fk")
    @Mapping(target = "userId_Fk", source = "originalReview.userId_Fk")
//    @Mapping(target = "reviewDate", source = "LocalDate.now()")
    @Mapping(target = "reviewId", source = "originalReview.reviewId")
    ProductReview entityMapperForUpdate(ProductReview originalReview, ProductReview updateReview);


    @Mapping(target = "productId_Fk", ignore = true)
    @Mapping(target = "userId_Fk", ignore = true)
    ProductReview dtoToEntityMapper(ReviewDto review);
}
