package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;

import java.util.List;

public interface ResolvedPostCachePort {

    void set(ResolvedPost resolvedPost);

    ResolvedPost get(Long postId);

    void delete(Long postId);

    List<ResolvedPost> multiGet(List<Long> postIds);
}
