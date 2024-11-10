package com.ns.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Entity
@Table(name = "Customer_Details")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_Id")
    private int id;

    @NotEmpty(message = "Name field can't be empty")
    @NotNull(message = "Name field can't be empty")
    private String name;

    @Email(message = "Please enter valid mail address")
    @Column(unique = true, updatable = false)
    private String email;

    @NotEmpty(message = "Password field can't be empty")
    @Size(min = 6, message = "Password should of min 6 character length")
    private String password;

    @Column(unique = true, updatable = false)
    @NotNull(message = "Phone number cant be null")
    private Long phoneNumber;

    @OneToMany(mappedBy = "userid_Fk", cascade = CascadeType.ALL
            , fetch = FetchType.LAZY)
    @JsonManagedReference(value = "user-address")
    private List<AddressEntity> addressList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "user-preference")
    private UserPreferenceEntity preferenceId;

    @OneToMany(targetEntity = ProductReview.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId_Fk", referencedColumnName = "user_Id")
    @JsonBackReference(value = "user-reviews")
    private List<ProductReview> userReviews;
}