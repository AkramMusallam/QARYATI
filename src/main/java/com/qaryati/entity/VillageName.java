package com.qaryati.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "village_names")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VillageName extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "name_type")
    private NameType nameType;

    @Column(name = "historical_context", columnDefinition = "TEXT")
    private String historicalContext;

    public enum Language {
        AR, EN
    }

    public enum NameType {
        FORMER, ALTERNATIVE, TRANSLITERATION
    }
}
