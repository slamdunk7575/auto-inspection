package com.yanggang.autoinspection.controller;

import com.yanggang.autoinspection.model.PostListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class PostListController {

    @GetMapping("/index/{userId}")
    public ResponseEntity<List<PostListDto>> listSubscribingPosts(
            @PathVariable("userId") Long userId) {
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PostListDto> searchPosts(
            @RequestParam("query") String query) {
        return ResponseEntity.internalServerError().build();
    }
}
