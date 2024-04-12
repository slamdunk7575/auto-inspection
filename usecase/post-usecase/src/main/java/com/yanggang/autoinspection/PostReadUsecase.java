package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.ResolvedPost;

public interface PostReadUsecase {

    ResolvedPost getById(Long postId);
}
