package com.yanggang.autoinspection.subscribingpost;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscribingPostRepository extends SubscribingPostCustomRepository, MongoRepository<SubscribingPostDocument, String> {
}
