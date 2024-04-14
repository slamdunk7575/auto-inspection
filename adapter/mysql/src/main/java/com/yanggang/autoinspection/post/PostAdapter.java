package com.yanggang.autoinspection.post;

import com.yanggang.autoinspection.PostPort;
import com.yanggang.autoinspection.content.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostAdapter implements PostPort {

    private final PostJpaRepository postJpaRepository;

    public PostAdapter(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    @Override
    public Post save(Post post) {
        PostEntity savedPost = postJpaRepository.save(PostEntityConverter.toEntity(post));
        return PostEntityConverter.toPost(savedPost);
    }

    @Override
    public Post findById(Long id) {
        PostEntity postEntity = postJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post 데이터를 찾지 못했습니다. id:" + id));
        return PostEntityConverter.toPost(postEntity);
    }
}
