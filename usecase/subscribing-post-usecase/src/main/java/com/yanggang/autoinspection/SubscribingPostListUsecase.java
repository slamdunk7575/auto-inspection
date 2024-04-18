package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public interface SubscribingPostListUsecase {

    List<ResolvedPost> listSubscribingInboxPosts(SubscribingListRequest request);


    @Getter
    class SubscribingListRequest {
        private final int pageNumber;
        private final Long followerUserId;

        @Builder
        public SubscribingListRequest(int pageNumber, Long followerUserId) {
            this.pageNumber = pageNumber;
            this.followerUserId = followerUserId;
        }
    }
}
