package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostUpdateService implements PostUpdateUsecase {

    private final PostPort postPort;

    public PostUpdateService(PostPort postPort) {
        this.postPort = postPort;
    }

    @Transactional
    @Override
    public Post update(Request request) {
        Post post = postPort.findById(request.getPostId());
        post.update(request.getTitle(), request.getContent(), request.getCategoryId());
        return postPort.save(post);
    }
}
