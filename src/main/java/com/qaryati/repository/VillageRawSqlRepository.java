package com.qaryati.repository;

import com.qaryati.dto.GovernorateStatsDto;
import com.qaryati.dto.NearbyVillageDto;
import com.qaryati.dto.VillageAnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VillageRawSqlRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<VillageAnalyticsDto> findVillageAnalyticsById(Long villageId) {
        String sql = """
                SELECT
                    v.id AS village_id,
                    v.current_name_ar,
                    v.current_name_en,
                    v.governorate,
                    (SELECT pr.population_value FROM population_records pr WHERE pr.village_id = v.id ORDER BY pr.reference_year DESC LIMIT 1) AS latest_population,
                    (SELECT pr.reference_year FROM population_records pr WHERE pr.village_id = v.id ORDER BY pr.reference_year DESC LIMIT 1) AS latest_population_year,
                    (SELECT COUNT(*) FROM village_names vn WHERE vn.village_id = v.id) AS historical_names_count,
                    (SELECT COUNT(*) FROM historical_events he WHERE he.village_id = v.id) AS historical_events_count,
                    (SELECT COUNT(*) FROM landmarks l WHERE l.village_id = v.id) AS landmarks_count,
                    (SELECT COUNT(*) FROM economy_items ei WHERE ei.village_id = v.id) AS economy_items_count,
                    (SELECT COUNT(*) FROM public_facilities pf WHERE pf.village_id = v.id) AS public_facilities_count
                FROM villages v
                WHERE v.id = ?
                """;

        List<VillageAnalyticsDto> results = jdbcTemplate.query(sql, (rs, rowNum) -> VillageAnalyticsDto.builder()
                .villageId(rs.getLong("village_id"))
                .currentNameAr(rs.getString("current_name_ar"))
                .currentNameEn(rs.getString("current_name_en"))
                .governorate(rs.getString("governorate"))
                .latestPopulation((Integer) rs.getObject("latest_population"))
                .latestPopulationYear((Integer) rs.getObject("latest_population_year"))
                .historicalNamesCount(rs.getLong("historical_names_count"))
                .historicalEventsCount(rs.getLong("historical_events_count"))
                .landmarksCount(rs.getLong("landmarks_count"))
                .economyItemsCount(rs.getLong("economy_items_count"))
                .publicFacilitiesCount(rs.getLong("public_facilities_count"))
                .build(), villageId);

        return results.stream().findFirst();
    }

    public List<GovernorateStatsDto> findGovernorateStatistics() {
        String sql = """
                SELECT
                    v.governorate,
                    COUNT(DISTINCT v.id) AS villages_count,
                    COUNT(DISTINCT l.id) AS total_landmarks_count,
                    COALESCE(SUM(latest_pop.population_value), 0) AS total_latest_population
                FROM villages v
                LEFT JOIN landmarks l ON l.village_id = v.id
                LEFT JOIN (
                    SELECT pr1.village_id, pr1.population_value
                    FROM population_records pr1
                    INNER JOIN (
                        SELECT village_id, MAX(reference_year) AS max_year
                        FROM population_records
                        GROUP BY village_id
                    ) pr2 ON pr1.village_id = pr2.village_id AND pr1.reference_year = pr2.max_year
                ) latest_pop ON latest_pop.village_id = v.id
                GROUP BY v.governorate
                ORDER BY villages_count DESC, v.governorate ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> GovernorateStatsDto.builder()
                .governorate(rs.getString("governorate"))
                .villagesCount(rs.getLong("villages_count"))
                .totalLandmarksCount(rs.getLong("total_landmarks_count"))
                .totalLatestPopulation(rs.getLong("total_latest_population"))
                .build());
    }

    public List<NearbyVillageDto> findNearbyVillages(double latitude, double longitude, double radiusKm, Long excludeVillageId) {
        String sql = """
                SELECT
                    id AS village_id,
                    current_name_ar,
                    current_name_en,
                    governorate,
                    latitude,
                    longitude,
                    (6371.0 * 2 * ASIN(SQRT(
                        POWER(SIN(RADIANS(latitude - ?) / 2), 2) +
                        COS(RADIANS(?)) * COS(RADIANS(latitude)) *
                        POWER(SIN(RADIANS(longitude - ?) / 2), 2)
                    ))) AS distance_km
                FROM villages
                WHERE latitude IS NOT NULL
                  AND longitude IS NOT NULL
                  AND (? IS NULL OR id <> ?)
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> NearbyVillageDto.builder()
                        .villageId(rs.getLong("village_id"))
                        .currentNameAr(rs.getString("current_name_ar"))
                        .currentNameEn(rs.getString("current_name_en"))
                        .governorate(rs.getString("governorate"))
                        .latitude(rs.getDouble("latitude"))
                        .longitude(rs.getDouble("longitude"))
                        .distanceKm(Math.round(rs.getDouble("distance_km") * 100.0) / 100.0)
                        .build(),
                latitude, latitude, longitude, excludeVillageId, excludeVillageId)
                .stream()
                .filter(v -> v.getDistanceKm() <= radiusKm)
                .sorted((a, b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .toList();
    }
}
