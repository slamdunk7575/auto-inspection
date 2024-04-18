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

        ResolvedPost resolvedPost = resolvedPostCachePort.get(postId);
        if (resolvedPost != null) {
            System.out.println("----------- 레디스 캐싱 조회 ----------");
            return resolvedPost;
        }

        System.out.println("----------- DB 조회 ----------");
        Post post = postPort.findById(postId);
        String userName = metadataPort.getUserNameByUserId(post.getUserId());
        String categoryName = metadataPort.getCategoryNameByCategoryId(post.getCategoryId());
        resolvedPost = ResolvedPost.generate(post, userName, categoryName);
        resolvedPostCachePort.set(resolvedPost);

        return resolvedPost;
    }

    @Override
    public List<ResolvedPost> resolvePostsByIds(List<Long> postIds) {
        return postIds.stream().map(this::resolvePostById).toList();
    }
}
