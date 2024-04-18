package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;

public interface PostSearchPort {

    void indexPost(InspectedPost post);

    void deletePost(Long id);
}
