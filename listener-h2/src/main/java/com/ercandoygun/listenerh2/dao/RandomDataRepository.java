package com.ercandoygun.listenerh2.dao;

import com.ercandoygun.listenerh2.entity.RandomDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomDataRepository extends JpaRepository<RandomDataEntity, Integer> {
}
