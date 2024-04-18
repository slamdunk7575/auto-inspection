package com.yanggang.autoinspection.controller;

import com.yanggang.autoinspection.SubscribingPostListUsecase;
import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import com.yanggang.autoinspection.model.PostListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class PostListController {

    private final SubscribingPostListUsecase subscribingPostListUsecase;

    public PostListController(SubscribingPostListUsecase subscribingPostListUsecase) {
        this.subscribingPostListUsecase = subscribingPostListUsecase;
    }

    @GetMapping("/index/{userId}")
    public ResponseEntity<List<PostListDto>> listSubscribingPosts(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page) {

        List<ResolvedPost> subscribingInboxPosts =
                subscribingPostListUsecase.listSubscribingInboxPosts(
                SubscribingPostListUsecase.SubscribingListRequest.builder()
                        .followerUserId(userId)
                        .pageNumber(page)
                        .build());

        return ResponseEntity.ok().body(subscribingInboxPosts.stream()
                .map(inboxPost -> PostListDto.toDto(inboxPost))
                .toList());
    }

    @GetMapping("/search")
    public ResponseEntity<PostListDto> searchPosts(
            @RequestParam("query") String query) {
        return ResponseEntity.internalServerError().build();
    }
}
