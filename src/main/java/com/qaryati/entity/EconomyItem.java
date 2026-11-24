package com.qaryati.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "economy_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EconomyItem extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
