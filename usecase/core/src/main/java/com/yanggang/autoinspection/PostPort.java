package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;

public interface PostPort {

    Post save(Post post);
    Post findById(Long id);
}
