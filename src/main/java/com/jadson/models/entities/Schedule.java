package com.jadson.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ScheduleMapper")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long id_User;

    @Column
    private Long schedule_type_id;

    @Column
    private Long active_type_id;

    @Column (nullable = false)
    private LocalDateTime create_date;

    @Column
    private LocalDateTime update_date;

    @Column
    private LocalDateTime completion_date;

    @Column
    private LocalDateTime cancellation_date;

    @Builder
    public Schedule(Long id_User, Long schedule_type_id, Long active_type_id, LocalDateTime create_date, LocalDateTime update_date, LocalDateTime completion_date, LocalDateTime cancellation_date) {
        this.id_User = id_User;
        this.schedule_type_id = schedule_type_id;
        this.active_type_id = active_type_id;
        this.create_date = create_date;
        this.update_date = update_date;
        this.completion_date = completion_date;
        this.cancellation_date = cancellation_date;
    }
}
