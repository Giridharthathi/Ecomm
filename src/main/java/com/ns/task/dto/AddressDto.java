package com.ns.task.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private int addressid;

    @NotEmpty(message = "Please enter house number")
    private String houseno;

    @NotEmpty(message = "Please enter your street name")
    private String streetName;

    @NotEmpty(message = "Please enter your City")
    private String city;

    @NotNull(message = "Please enter pincode")
    private Long pincode;

    @NotEmpty(message = "Please enter your state")
    private String state;
    private int userid;
    private String userName;
}