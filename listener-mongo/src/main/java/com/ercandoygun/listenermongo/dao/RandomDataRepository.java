package com.ercandoygun.listenermongo.dao;

import com.ercandoygun.listenermongo.entity.RandomDataDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomDataRepository extends MongoRepository<RandomDataDocument, String> {

    RandomDataDocument findFirstByOrderByIdDesc();
}
