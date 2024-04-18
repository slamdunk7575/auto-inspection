package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;

import java.util.List;

public interface PostPort {

    Post save(Post post);
    Post findById(Long id);
    List<Post> listByIds(List<Long> postIds);
}
