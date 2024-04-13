package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.ResolvedPost;

import java.util.List;

public interface PostResolvingHelpUsecase {

    ResolvedPost resolvePostById(Long postId);

    List<ResolvedPost> resolvePostsByIds(List<Long> postIds);
}
