package com.ns.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="review")
@Entity
@Getter
@Setter
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reviewId;

    private double rating;

    @Column(name = "description")
    private String reviewDescription;

    private LocalDate reviewdate;

    @ManyToOne
    @JoinColumn(name = "productId_Fk")
    @JsonBackReference(value = "product-reviews")
    private ProductEntity productId_Fk;

    @ManyToOne
    @JoinColumn(name = "userId_Fk")
    @JsonBackReference(value = "user-review")
    private UserEntity userId_Fk;
}
