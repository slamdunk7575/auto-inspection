package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import com.yanggang.autoinspection.content.model.ResolvedPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostResolvingHelpService implements PostResolvingHelpUsecase {

    private final PostPort postPort;
    private final MetadataPort metadataPort;

    public PostResolvingHelpService(PostPort postPort, MetadataPort metadataPort) {
        this.postPort = postPort;
        this.metadataPort = metadataPort;
    }

    @Override
    public ResolvedPost resolvePostById(Long postId) {
        Post post = postPort.findById(postId);
        String userName = metadataPort.getUserNameByUserId(post.getUserId());
        String categoryName = metadataPort.getCategoryNameByCategoryId(post.getCategoryId());
        ResolvedPost resolvedPost = ResolvedPost.generate(post, userName, categoryName);
        return resolvedPost;
    }

    @Override
    public List<ResolvedPost> resolvePostsByIds(List<Long> postIds) {
        return postIds.stream().map(this::resolvePostById).toList();
    }
}
