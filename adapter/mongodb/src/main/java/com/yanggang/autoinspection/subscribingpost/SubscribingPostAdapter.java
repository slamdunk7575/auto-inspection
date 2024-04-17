package com.yanggang.autoinspection.subscribingpost;

import com.yanggang.autoinspection.SubscribingPostPort;
import com.yanggang.autoinspection.content.post.model.Post;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscribingPostAdapter implements SubscribingPostPort {

    private final SubscribingPostRepository subscribingPostRepository;

    public SubscribingPostAdapter(SubscribingPostRepository subscribingPostRepository) {
        this.subscribingPostRepository = subscribingPostRepository;
    }

    @Override
    public void addPostToFollowerInboxes(Post post, List<Long> followerUserIds) {
        List<SubscribingPostDocument> subscribingPostDocuments = followerUserIds.stream()
                .map(followerUserId -> SubscribingPostDocument.of(post, followerUserId))
                .toList();

        subscribingPostRepository.saveAll(subscribingPostDocuments);
    }

    @Override
    public List<Long> listPostIdsByFollowerUserIdWithPagination(Long followerUserId, int pageNumber, int pageSize) {
        List<SubscribingPostDocument> subscribingPostDocuments =
                subscribingPostRepository.findByFollowerUserIdWithPagination(followerUserId, pageNumber, pageSize);

        return subscribingPostDocuments.stream()
                .map(subscribingPostDocument -> subscribingPostDocument.getPostId())
                .toList();
    }
}
