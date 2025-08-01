package com.jadson.models.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "Task")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Task {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 100)
    private String title;

    @Column (nullable = false, length = 500)
    private String description;

    @Column (nullable = false)
    private LocalDate date;

    @Column (nullable = false)
    private LocalTime hour;

    @Builder
    public Task(String title, String description, LocalDate date, LocalTime hour) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.hour = hour;
    }

}
