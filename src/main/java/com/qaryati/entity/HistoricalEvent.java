package com.qaryati.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "historical_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricalEvent extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "event_year")
    private Integer eventYear;

    @Column(name = "period", length = 100)
    private String period;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "source_reference", length = 500)
    private String sourceReference;
}
