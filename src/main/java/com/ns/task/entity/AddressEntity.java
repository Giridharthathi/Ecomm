package com.ns.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int addressid;

    private String houseno;

    @NotEmpty(message = "street cannot be empty")
    private String streetName;

    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotNull(message = "Pincode cannot be empty")
    private Long pincode;

    @NotEmpty(message = "State cannot be empty")
    private String state;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid_Fk")
    @JsonBackReference(value = "user-address")
    private UserEntity userid_Fk;
}