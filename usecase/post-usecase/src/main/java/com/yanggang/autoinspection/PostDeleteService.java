package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import org.springframework.stereotype.Service;

@Service
public class PostDeleteService implements PostDeleteUsecase {

    private final PostPort postPort;

    public PostDeleteService(PostPort postPort) {
        this.postPort = postPort;
    }

    @Override
    public Post delete(Request request) {
        Post post = postPort.findById(request.getPostId());
        post.delete();
        return postPort.save(post);
    }
}
