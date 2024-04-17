package com.yanggang.autoinspection.subscribingpost;

import com.yanggang.autoinspection.content.post.model.Post;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collation = "subscribingInboxPosts")
public class SubscribingPostDocument {

    @Id
    private String id; // postId + followerUserId
    private Long postId;
    private Long followerUserId; // follower(구독자) 아이디
    private LocalDateTime postCreatedDate; // 컨텐츠 생성 날짜
    private LocalDateTime addedDate; // follower(구독자)의 구독 목록에 반영된 날짜
    private boolean read; // 해당 컨텐츠 조회 여부

    @Builder
    private SubscribingPostDocument(String id, Long postId, Long followerUserId,
                                   LocalDateTime postCreatedDate, LocalDateTime addedDate, boolean read) {
        this.id = id;
        this.postId = postId;
        this.followerUserId = followerUserId;
        this.postCreatedDate = postCreatedDate;
        this.addedDate = addedDate;
        this.read = read;
    }

    public static SubscribingPostDocument of(Post post, Long followerUserId) {
        return SubscribingPostDocument.builder()
                .id(generateDocumentId(post.getId(), followerUserId))
                .postId(post.getId())
                .followerUserId(followerUserId)
                .postCreatedDate(post.getCreatedDate())
                .addedDate(LocalDateTime.now())
                .read(false)
                .build();
    }

    private static String generateDocumentId(Long postId, Long followerUserId) {
        return postId + "_" + followerUserId;
    }

    public Long getPostId() {
        return postId;
    }
}
