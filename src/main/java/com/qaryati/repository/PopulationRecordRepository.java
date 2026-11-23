package com.qaryati.repository;
import com.qaryati.entity.PopulationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PopulationRecordRepository extends JpaRepository<PopulationRecord, Long> {
    List<PopulationRecord> findByVillageIdOrderByReferenceYearDesc(Long villageId);
    boolean existsByVillageIdAndReferenceYear(Long villageId, Integer referenceYear);
}
