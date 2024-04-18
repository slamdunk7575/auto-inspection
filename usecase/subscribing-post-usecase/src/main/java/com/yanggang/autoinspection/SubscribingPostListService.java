package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribingPostListService implements SubscribingPostListUsecase {

    private static final int PAGE_SIZE = 5;

    private final SubscribingPostPort subscribingPostPort;
    private final PostResolvingHelpUsecase postResolvingHelpUsecase;

    public SubscribingPostListService(SubscribingPostPort subscribingPostPort,
                                      PostResolvingHelpUsecase postResolvingHelpUsecase) {
        this.subscribingPostPort = subscribingPostPort;
        this.postResolvingHelpUsecase = postResolvingHelpUsecase;
    }

    @Override
    public List<ResolvedPost> listSubscribingInboxPosts(SubscribingListRequest request) {
        List<Long> subscribingPostIds = subscribingPostPort.listPostIdsByFollowerUserIdWithPagination(
                request.getFollowerUserId(),
                request.getPageNumber(),
                PAGE_SIZE
        );

        return postResolvingHelpUsecase.resolvePostsByIds(subscribingPostIds);
    }
}
