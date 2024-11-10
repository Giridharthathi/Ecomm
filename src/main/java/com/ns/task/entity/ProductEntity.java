package com.ns.task.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "product")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_Id")
    private int productId;

    @NotEmpty(message = "Product Name cannot be empty")
    private String name;

    @NotEmpty(message = "Brand field cannot be empty")
    private String brand;

    @Positive(message = "stock should be a positive number")
    private int stock;

    @NotEmpty(message = "Product description cannot be empty")
    private String description;

    @Positive(message = "Price should be positive")
    private double price;

    @NotEmpty(message = "Category cannot be empty")
    private String category;

    @OneToMany(mappedBy = "productId_Fk", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonManagedReference(value = "product-reviews")
    private List<ProductReview> review;

    @Column(name = "totalReviews")
    private Long totalReviews;

    private double avgReview;
}