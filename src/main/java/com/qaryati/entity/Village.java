package com.qaryati.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "villages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Village extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_name_ar", nullable = false)
    private String currentNameAr;

    @Column(name = "current_name_en")
    private String currentNameEn;

    @Column(name = "meaning_or_origin", columnDefinition = "TEXT")
    private String meaningOrOrigin;

    @Column(name = "governorate", nullable = false)
    private String governorate;

    @Column(name = "administrative_affiliation")
    private String administrativeAffiliation;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "location_description", columnDefinition = "TEXT")
    private String locationDescription;

    @Column(name = "elevation")
    private Integer elevation;

    @Column(name = "land_area")
    private Double landArea;

    @Column(name = "nearby_locations", columnDefinition = "TEXT")
    private String nearbyLocations;

    @Column(name = "historical_overview", columnDefinition = "TEXT")
    private String historicalOverview;

    @Column(name = "cultural_characteristics", columnDefinition = "TEXT")
    private String culturalCharacteristics;
}
