package com.brzezinski.allegrocontent.video;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

interface MongoDBVideoRepository extends ReactiveMongoRepository<Video, UUID> {

}
