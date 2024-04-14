package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import org.springframework.stereotype.Service;

@Service
public class PostDeleteService implements PostDeleteUsecase {

    private final PostPort postPort;
    private final PostMessageProducePort postMessageProducePort;

    public PostDeleteService(PostPort postPort, PostMessageProducePort postMessageProducePort) {
        this.postPort = postPort;
        this.postMessageProducePort = postMessageProducePort;
    }

    @Override
    public Post delete(Request request) {
        Post post = postPort.findById(request.getPostId());
        post.delete();
        Post deletedPost = postPort.save(post);
        postMessageProducePort.sendDeleteMessage(deletedPost.getId());
        return deletedPost;
    }
}
