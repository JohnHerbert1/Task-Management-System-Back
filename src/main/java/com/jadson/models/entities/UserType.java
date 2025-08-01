package com.jadson.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User_Type")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 500)
    private String description;

    @Builder
    public UserType(String description) {
        this.description = description;
    }
}
