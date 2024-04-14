package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostUpdateService implements PostUpdateUsecase {

    private final PostPort postPort;
    private final PostMessageProducePort postMessageProducePort;

    public PostUpdateService(PostPort postPort, PostMessageProducePort postMessageProducePort) {
        this.postPort = postPort;
        this.postMessageProducePort = postMessageProducePort;
    }

    @Transactional
    @Override
    public Post update(Request request) {
        Post post = postPort.findById(request.getPostId());
        Post updatedPost = post.update(request.getTitle(), request.getContent(), request.getCategoryId());
        postPort.save(updatedPost);
        postMessageProducePort.sendUpdateMessage(updatedPost);
        return updatedPost;
    }
}
