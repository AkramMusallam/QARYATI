package com.qaryati.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "population_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopulationRecord extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @Column(name = "population_value", nullable = false)
    private Integer populationValue;

    @Column(name = "reference_year", nullable = false)
    private Integer referenceYear;

    @Column(name = "source_information", nullable = false)
    private String sourceInformation;
}
