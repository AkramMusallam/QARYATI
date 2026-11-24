package com.qaryati.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "public_facilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicFacility extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "facility_type", nullable = false, length = 100)
    private String facilityType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
