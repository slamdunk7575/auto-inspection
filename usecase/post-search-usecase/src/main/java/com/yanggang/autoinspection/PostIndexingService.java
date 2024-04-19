package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class PostIndexingService implements PostIndexingUsecase {

    private final PostSearchPort postSearchPort;

    public PostIndexingService(PostSearchPort postSearchPort) {
        this.postSearchPort = postSearchPort;
    }

    @Override
    public void save(InspectedPost post) {
        postSearchPort.indexPost(post);
    }

    @Override
    public void delete(Long postId) {
        postSearchPort.deletePost(postId);
    }
}
