package com.ns.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDto {
    private int id;
    private String name;
    private String email;
    private Long phoneNumber;

    @JsonIgnore
    private String password;
    private String token;
}
