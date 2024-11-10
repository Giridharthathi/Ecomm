package com.ns.task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class LoginDto {
    @NotEmpty(message = "mailId cannot be empty, please fill the field")
    @Email(message = "Please enter valid email")
    private String mail;
    @NotEmpty(message = "Password cannot be null, please fill the field")
    private String password;
}