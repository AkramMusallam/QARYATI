package com.qaryati.repository;

import com.qaryati.entity.EconomyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EconomyItemRepository extends JpaRepository<EconomyItem, Long> {
    List<EconomyItem> findByVillageId(Long villageId);
}
