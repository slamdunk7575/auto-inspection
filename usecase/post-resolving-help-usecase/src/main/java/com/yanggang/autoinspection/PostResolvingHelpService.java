package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;
import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        if (postIds == null || postIds.isEmpty()) {
            return List.of();
        }

        // Cache 에서 한번에 조회 (MultiGet)
        List<ResolvedPost> resolvedPostsCaches = new ArrayList<>();
        resolvedPostsCaches.addAll(resolvedPostCachePort.multiGet(postIds));

        // Cache 에 없는 데이터 조회 (7일 이상 지난 데이터 or 새로 조회하는 데이터)
        List<Long> missingPostIds = postIds.stream()
                .filter(postId -> resolvedPostsCaches.stream()
                        .noneMatch(resolvedPost -> resolvedPost.getId().equals(postId))
                ).toList();

        List<Post> missingPosts = postPort.listByIds(missingPostIds);
        List<ResolvedPost> missingResolvedPosts = missingPosts.stream()
                .map(this::resolvedPost)
                .filter(Objects::nonNull)
                .toList();

        resolvedPostsCaches.addAll(missingResolvedPosts);

        // 재정렬
        Map<Long, ResolvedPost> resolvedPostMap = resolvedPostsCaches.stream()
                .collect(Collectors.toMap(ResolvedPost::getId, Function.identity()));

        List<ResolvedPost> sortingResolvedPostsCaches = postIds.stream()
                .map(resolvedPostMap::get)
                .filter(Objects::nonNull)
                .toList();

        return sortingResolvedPostsCaches;
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
