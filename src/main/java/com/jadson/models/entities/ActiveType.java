package com.jadson.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Active_Type")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ActiveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 500)
    private String description;

    @Builder
    public ActiveType(String description) {
        this.description = description;
    }
}
