package com.jadson.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Schedule_Type")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ScheduleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 500)
    private String description;

    @Builder
    public ScheduleType(String description) {
        this.description = description;
    }
}
