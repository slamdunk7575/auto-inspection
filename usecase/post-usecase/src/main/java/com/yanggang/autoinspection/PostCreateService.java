package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostCreateService implements PostCreateUsecase {

    private final PostPort postPort;
    private final PostMessageProducePort postMessageProducePort;

    public PostCreateService(PostPort postPort, PostMessageProducePort postMessageProducePort) {
        this.postPort = postPort;
        this.postMessageProducePort = postMessageProducePort;
    }

    @Transactional
    @Override
    public Post create(Request request) {
        LocalDateTime now = LocalDateTime.now();
        Post savedPost = postPort.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .userId(request.getUserId())
                .categoryId(request.getCategoryId())
                .createdDate(now)
                .updatedDate(now)
                .build());

        postMessageProducePort.sendCreateMessage(savedPost);
        return savedPost;
    }
}
