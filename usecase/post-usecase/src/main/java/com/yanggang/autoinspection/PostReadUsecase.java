package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.ResolvedPost;

import java.lang.module.ResolutionException;

public interface PostReadUsecase {

    ResolvedPost getById(Long postId);
}
