package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;

public interface ResolvedPostCachePort {

    void set(ResolvedPost resolvedPost);
    ResolvedPost get(Long postId);
}
