package com.ns.task.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ns.task.entity.UserPreferenceEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateDetailDto {
    private int id;
    @NotEmpty(message = "Name field cant be empty")
    private String name;

    @Email(message = "Please enter valid mail address")
    private String email;

    @NotEmpty
    @Size(min = 6, message = "Password should of min 6 character length")
    private String password;

    @NotNull(message = "Phone number field cant be empty")
    private Long phoneNumber;

    private UserPreferenceEntity preferenceId;
}
