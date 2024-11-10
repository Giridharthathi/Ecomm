package com.ns.task.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ns.task.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPreferenceDto {
    private int id;
    private boolean emailNotification;
    private boolean smsNotification;
    private UserEntity user_Id;
}
