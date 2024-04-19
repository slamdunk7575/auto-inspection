package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;

import java.util.List;

public interface PostSearchUsecase {

    List<ResolvedPost> getSearchResultByKeyword(String keyword, int pageNumber);
}
