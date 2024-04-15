package com.yanggang.autoinspection.controller;

import com.yanggang.autoinspection.PostInspectUsecase;
import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;
import com.yanggang.autoinspection.content.post.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/internal")
public class InternalController {

    private final PostInspectUsecase postInspectUsecase;

    public InternalController(PostInspectUsecase postInspectUsecase) {
        this.postInspectUsecase = postInspectUsecase;
    }

    @GetMapping
    public InspectedPost inspectedPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Long categoryId
    ) {
        LocalDateTime now = LocalDateTime.now();
        return postInspectUsecase.inspectAndGetIfValid(
                Post.builder()
                        .title(title)
                        .content(content)
                        .categoryId(categoryId)
                        .createdDate(now)
                        .updatedDate(now)
                        .build());
    }
}
