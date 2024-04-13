package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.ResolvedPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostResolvingHelpService implements PostResolvingHelpUsecase {

    private final MetadataPort metadataPort;

    public PostResolvingHelpService(MetadataPort metadataPort) {
        this.metadataPort = metadataPort;
    }

    @Override
    public ResolvedPost resolvePostById(Long postId) {
        ResolvedPost resolvedPost = null;
        return resolvedPost;
    }

    @Override
    public List<ResolvedPost> resolvePostsByIds(List<Long> postIds) {
        return postIds.stream().map(this::resolvePostById).toList();
    }
}
