package com.qaryati.specification;

import com.qaryati.entity.Village;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class VillageSpecification {

    private static final char LIKE_ESCAPE = '\\';

    private VillageSpecification() {
    }

    public static Specification<Village> filterAndSearch(String search, String governorate) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                String searchPattern = "%" + escapeLike(search.trim().toLowerCase()) + "%";
                predicates.add(cb.or(
                        like(cb, root.get("currentNameAr"), searchPattern),
                        like(cb, root.get("currentNameEn"), searchPattern),
                        like(cb, root.get("administrativeAffiliation"), searchPattern)
                ));
            }

            if (governorate != null && !governorate.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("governorate")), governorate.trim().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Predicate like(CriteriaBuilder cb, Expression<String> field, String pattern) {
        return cb.like(cb.lower(field), pattern, LIKE_ESCAPE);
    }

    private static String escapeLike(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
}
