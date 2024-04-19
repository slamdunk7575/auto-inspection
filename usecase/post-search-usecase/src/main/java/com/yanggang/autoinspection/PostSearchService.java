package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSearchService implements PostSearchUsecase {

    private static final int PAGE_SIZE = 5;
    private final PostSearchPort postSearchPort;
    private final PostResolvingHelpUsecase postResolvingHelpUsecase;

    public PostSearchService(PostSearchPort postSearchPort,
                             PostResolvingHelpUsecase postResolvingHelpUsecase) {
        this.postSearchPort = postSearchPort;
        this.postResolvingHelpUsecase = postResolvingHelpUsecase;
    }

    @Override
    public List<ResolvedPost> getSearchResultByKeyword(String keyword, int pageNumber) {
        List<Long> searchedPostIds = postSearchPort.searchPostIdsByKeyword(keyword, pageNumber, PAGE_SIZE);
        return postResolvingHelpUsecase.resolvePostsByIds(searchedPostIds);
    }
}
