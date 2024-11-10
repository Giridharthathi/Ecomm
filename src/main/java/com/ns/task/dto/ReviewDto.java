package com.ns.task.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ns.task.entity.ProductEntity;
import com.ns.task.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
public class ReviewDto {

    private int reviewId;
    private double rating;
    private String reviewDescription;
    private LocalDate reviewdate;
    private int productId_Fk;
    private int userId_Fk;
}
