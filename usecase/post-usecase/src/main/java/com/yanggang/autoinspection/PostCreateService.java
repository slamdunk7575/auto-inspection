package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostCreateService implements PostCreateUsecase {

    private final PostPort postPort;

    public PostCreateService(PostPort postPort) {
        this.postPort = postPort;
    }

    @Transactional
    @Override
    public Post create(Request request) {
        LocalDateTime now = LocalDateTime.now();
        return postPort.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .userId(request.getUserId())
                .categoryId(request.getCategoryId())
                .createdDate(now)
                .updatedDate(now)
                .build());
    }
}
