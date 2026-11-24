package com.qaryati.repository;

import com.qaryati.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Long> {
    List<Landmark> findByVillageId(Long villageId);
}
