package com.ns.task.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ProductDetailDto {
    private int productId;
    @NotEmpty(message = "Product name can not be empty")
    @NotNull(message = "Product name can not be empty")
    private String name;

    @NotEmpty(message = "Product brand can not be empty")
    @NotNull(message = "Product brand can not be empty")
    private String brandName;

    @PositiveOrZero(message = "Stock should be greater than zero")
    private int stock;

    @NotEmpty(message = "Product Category can not be empty")
    @NotNull(message = "Product Category can not be empty")
    private String category;

    @NotEmpty(message = "Product Description can not be empty")
    @NotNull(message = "Product Description can not be empty")
    private String description;

    @PositiveOrZero(message = "Price should be greater than zero")
    private double price;

    private List<ReviewDto> productReviews;
}