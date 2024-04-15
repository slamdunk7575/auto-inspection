package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import org.springframework.stereotype.Service;

@Service
public class PostReadService implements PostReadUsecase {
    @Override
    public ResolvedPost getById(Long postId) {
        return null;
    }
}
