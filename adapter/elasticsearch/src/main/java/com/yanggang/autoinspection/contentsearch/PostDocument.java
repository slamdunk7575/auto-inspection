package com.yanggang.autoinspection.contentsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Document(indexName = "post-1") // alias 있기 때문에 버져닝
public class PostDocument {

    @Id
    private Long id;
    private String title;
    private String content;
    private String categoryName;
    private List<String> tags;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime indexedDate;

    @Builder
    public PostDocument(Long id, String title, String content, String categoryName,
                        List<String> tags, LocalDateTime indexedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.tags = tags;
        this.indexedDate = indexedDate;
    }
}
