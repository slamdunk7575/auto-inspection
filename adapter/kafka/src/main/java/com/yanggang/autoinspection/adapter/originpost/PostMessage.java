package com.yanggang.autoinspection.adapter.originpost;

import com.yanggang.autoinspection.adapter.common.OperationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostMessage {
    private Long id;
    private Payload payload;
    private OperationType operationType;

    @Builder
    public PostMessage(Long id,
                       Payload payload,
                       OperationType operationType) {
        this.id = id;
        this.payload = payload;
        this.operationType = operationType;
    }

    @Getter
    @NoArgsConstructor
    public static class Payload {
        private Long id;
        private String title;
        private String content;
        private Long userId;
        private Long categoryId;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private LocalDateTime deletedDate;

        @Builder
        public Payload(Long id, String title, String content, Long userId, Long categoryId,
                       LocalDateTime createdDate, LocalDateTime updatedDate, LocalDateTime deletedDate) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.userId = userId;
            this.categoryId = categoryId;
            this.createdDate = createdDate;
            this.updatedDate = updatedDate;
            this.deletedDate = deletedDate;
        }

        @Override
        public String toString() {
            return "Payload{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", userId=" + userId +
                    ", categoryId=" + categoryId +
                    ", createdDate=" + createdDate +
                    ", updatedDate=" + updatedDate +
                    ", deletedDate=" + deletedDate +
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
}
