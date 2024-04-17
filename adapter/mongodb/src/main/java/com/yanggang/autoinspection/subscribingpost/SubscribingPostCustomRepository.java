package com.yanggang.autoinspection.subscribingpost;

import java.util.List;

public interface SubscribingPostCustomRepository {

    List<SubscribingPostDocument> findByFollowerUserIdWithPagination(Long followerUserId, int pageNumber, int pageSize);
}
