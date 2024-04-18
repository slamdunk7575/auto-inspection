package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribingPostAddToInboxService implements SubscribingPostAddToInboxUsecase {

    private final SubscribingPostPort subscribingPostPort;
    private final MetadataPort metadataPort;

    public SubscribingPostAddToInboxService(SubscribingPostPort subscribingPostPort,
                                            MetadataPort metadataPort) {
        this.subscribingPostPort = subscribingPostPort;
        this.metadataPort = metadataPort;
    }

    @Override
    public void saveSubscribingInboxPost(Post post) {
        List<Long> followerIds = metadataPort.listFollowerIdsByUserID(post.getUserId());
        subscribingPostPort.addPostToFollowerInboxes(post, followerIds);
    }
}
