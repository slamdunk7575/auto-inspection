package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;
import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostResolvingHelpService implements PostResolvingHelpUsecase {

    private final PostPort postPort;
    private final MetadataPort metadataPort;
    private final ResolvedPostCachePort resolvedPostCachePort;

    public PostResolvingHelpService(PostPort postPort,
                                    MetadataPort metadataPort,
                                    ResolvedPostCachePort resolvedPostCachePort) {
        this.postPort = postPort;
        this.metadataPort = metadataPort;
        this.resolvedPostCachePort = resolvedPostCachePort;
    }

    @Override
    public ResolvedPost resolvePostById(Long postId) {
        // Cache 조회
        ResolvedPost resolvedPost = resolvedPostCachePort.get(postId);
        if (resolvedPost != null) {
            return resolvedPost;
        }

        // DB 조회
        Post post = postPort.findById(postId);
        if (post != null) {
            resolvedPost = resolvedPost(post);
        }

        return resolvedPost;
    }

    @Override
    public List<ResolvedPost> resolvePostsByIds(List<Long> postIds) {
        return postIds.stream().map(this::resolvePostById).toList();
    }

    @Override
    public void resolvePostAndSave(Post post) {
        ResolvedPost resolvedPost = resolvedPost(post);
        if (resolvedPost != null) {
            resolvedPostCachePort.set(resolvedPost);
        }
    }

    @Override
    public void deleteResolvedPost(Long postId) {
        resolvedPostCachePort.delete(postId);
    }

    private ResolvedPost resolvedPost(Post post) {
        if (post == null) {
            return null;
        }

        String userName = metadataPort.getUserNameByUserId(post.getUserId());
        String categoryName = metadataPort.getCategoryNameByCategoryId(post.getCategoryId());
        ResolvedPost resolvedPost = ResolvedPost.generate(post, userName, categoryName);
        resolvedPostCachePort.set(resolvedPost);
        return resolvedPost;
    }
}
