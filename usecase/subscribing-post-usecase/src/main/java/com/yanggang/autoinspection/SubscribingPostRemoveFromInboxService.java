package com.yanggang.autoinspection;

import org.springframework.stereotype.Service;

@Service
public class SubscribingPostRemoveFromInboxService implements SubscribingPostRemoveFromInboxUsecase {

    private final SubscribingPostPort subscribingPostPort;

    public SubscribingPostRemoveFromInboxService(SubscribingPostPort subscribingPostPort) {
        this.subscribingPostPort = subscribingPostPort;
    }

    @Override
    public void removeSubscribingInboxPost(Long postId) {
        subscribingPostPort.removePostFromFollowerInboxes(postId);
    }
}
