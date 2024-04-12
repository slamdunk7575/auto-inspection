package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostCreateService implements PostCreateUsecase {

    @Override
    public Post create(Request request) {
        LocalDateTime now = LocalDateTime.now();
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .userId(request.getUserId())
                .categoryId(request.getCategoryId())
                .createdDate(now)
                .updatedDate(now)
                .build();
    }
}
