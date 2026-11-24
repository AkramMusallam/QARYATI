package com.qaryati.repository;

import com.qaryati.entity.PublicFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicFacilityRepository extends JpaRepository<PublicFacility, Long> {
    List<PublicFacility> findByVillageId(Long villageId);
}
