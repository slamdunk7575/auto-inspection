package com.yanggang.autoinspection.controller;

import com.yanggang.autoinspection.PostCreateUsecase;
import com.yanggang.autoinspection.PostDeleteUsecase;
import com.yanggang.autoinspection.PostReadUsecase;
import com.yanggang.autoinspection.PostUpdateUsecase;
import com.yanggang.autoinspection.content.post.model.Post;
import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import com.yanggang.autoinspection.model.PostCreateRequest;
import com.yanggang.autoinspection.model.PostDetailResponseDto;
import com.yanggang.autoinspection.model.PostResponseDto;
import com.yanggang.autoinspection.model.PostUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostCreateUsecase postCreateUsecase;
    private final PostReadUsecase postReadUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;

    public PostController(PostCreateUsecase postCreateUsecase,
                          PostReadUsecase postReadUsecase,
                          PostUpdateUsecase postUpdateUsecase,
                          PostDeleteUsecase postDeleteUsecase) {
        this.postCreateUsecase = postCreateUsecase;
        this.postReadUsecase = postReadUsecase;
        this.postUpdateUsecase = postUpdateUsecase;
        this.postDeleteUsecase = postDeleteUsecase;
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        Post savedPost = postCreateUsecase.create(postCreateRequest.toRequest());
        return ResponseEntity.ok().body(PostResponseDto.toDto(savedPost));
    }

    @GetMapping("/{postId}/detail")
    public ResponseEntity<PostDetailResponseDto> readPost(@PathVariable("postId") Long postId) {
        ResolvedPost resolvedPost = postReadUsecase.getById(postId);

        if (resolvedPost == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(PostDetailResponseDto.toDto(resolvedPost));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        Post updatedPost = postUpdateUsecase.update(postUpdateRequest.toRequest(postId));
        return ResponseEntity.ok().body(PostResponseDto.toDto(updatedPost));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponseDto> deletePost(
        @PathVariable("postId") Long postId
    ) {
        Post deletedPost = postDeleteUsecase.delete(new PostDeleteUsecase.Request(postId));
        return ResponseEntity.ok().body(PostResponseDto.toDto(deletedPost));
    }

}
