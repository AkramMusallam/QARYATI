package com.qaryati.repository;
import com.qaryati.entity.VillageName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VillageNameRepository extends JpaRepository<VillageName, Long> {
    List<VillageName> findByVillageId(Long villageId);
}
