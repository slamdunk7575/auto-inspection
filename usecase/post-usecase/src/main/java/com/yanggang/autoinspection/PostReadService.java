package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import org.springframework.stereotype.Service;

@Service
public class PostReadService implements PostReadUsecase {

    private final PostResolvingHelpUsecase postResolvingHelpUsecase;

    public PostReadService(PostResolvingHelpUsecase postResolvingHelpUsecase) {
        this.postResolvingHelpUsecase = postResolvingHelpUsecase;
    }

    @Override
    public ResolvedPost getById(Long postId) {
        return postResolvingHelpUsecase.resolvePostById(postId);
    }
}
