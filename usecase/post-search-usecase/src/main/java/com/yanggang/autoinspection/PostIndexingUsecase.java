package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;

public interface PostIndexingUsecase {
    void save(InspectedPost post);
    void delete(Long postId);
}
