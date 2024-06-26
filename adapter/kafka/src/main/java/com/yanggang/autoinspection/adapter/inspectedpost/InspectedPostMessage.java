  package com.yanggang.autoinspection.adapter.inspectedpost;

import com.yanggang.autoinspection.adapter.common.OperationType;
import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;
import com.yanggang.autoinspection.content.post.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class InspectedPostMessage {
    private Long id;
    private Payload payload;
    private OperationType operationType;

    @Builder
    public InspectedPostMessage(Long id,
                                Payload payload,
                                OperationType operationType) {
        this.id = id;
        this.payload = payload;
        this.operationType = operationType;
    }

    @Getter
    @NoArgsConstructor
    public static class Payload {
        private Post post;
        private String categoryName;
        private List<String> autoGeneratedTags;
        private LocalDateTime inspectedDate;

        @Builder
        public Payload(Post post, String categoryName,
                       List<String> autoGeneratedTags, LocalDateTime inspectedDate) {
            this.post = post;
            this.categoryName = categoryName;
            this.autoGeneratedTags = autoGeneratedTags;
            this.inspectedDate = inspectedDate;
        }

        @Override
        public String toString() {
            return "Payload{" +
                    "post=" + post +
                    ", categoryName='" + categoryName + '\'' +
                    ", autoGeneratedTags=" + autoGeneratedTags +
                    ", inspectedDate=" + inspectedDate +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OriginalPostMessage{" +
                "id=" + id +
                ", payload=" + payload +
                ", operationType=" + operationType +
                '}';
    }

    public InspectedPost toModel() {
        return InspectedPost.builder()
                .post(this.payload.getPost())
                .categoryName(this.payload.getCategoryName())
                .autoGeneratedTags(this.payload.getAutoGeneratedTags())
                .inspectedDate(this.payload.getInspectedDate())
                .build();
    }
}
