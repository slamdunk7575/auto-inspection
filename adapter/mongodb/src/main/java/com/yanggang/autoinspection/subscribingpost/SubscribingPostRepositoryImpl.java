package com.yanggang.autoinspection.subscribingpost;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscribingPostRepositoryImpl implements SubscribingPostCustomRepository {

    private final MongoTemplate mongoTemplate;

    public SubscribingPostRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<SubscribingPostDocument> findByFollowerUserIdWithPagination(Long followerUserId, int pageNumber, int pageSize) {
        Query query = new Query()
                .addCriteria(Criteria.where("followerUserId").is(followerUserId))
                .with(
                        PageRequest.of(
                                pageNumber,
                                pageSize,
                                Sort.by(Sort.Direction.DESC, "postCreatedDate"))

                );

        System.out.println(query);
        return mongoTemplate.find(query, SubscribingPostDocument.class, "subscribingInboxPosts");
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        Query query = new Query()
                .addCriteria(Criteria.where("postId").is(postId));
        mongoTemplate.remove(query, SubscribingPostDocument.class);

    }
}
