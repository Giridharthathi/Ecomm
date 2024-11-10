package com.ns.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_preference")
@Entity
@Getter
@Setter
public class UserPreferenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean emailNotification;
    private boolean smsNotification;

    @OneToOne(mappedBy = "preferenceId",fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-preference")
    private UserEntity user_Id;
}
