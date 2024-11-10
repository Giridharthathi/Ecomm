package com.ns.task.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductBannerDto {
    private int productId;
    private String name;
    private String brand;
    private double price;
//    private String productImageEncodedData;
    private int productRating;
}
