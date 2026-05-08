package com.assignment.backend.controller;

import com.assignment.backend.dto.CreateCommentRequest;
import com.assignment.backend.dto.CreatePostRequest;
import com.assignment.backend.entity.Comment;
import com.assignment.backend.entity.Post;
import com.assignment.backend.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {

        return postService.createPost(request);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request
    ) {

        return postService.createComment(
                postId,
                request
        );
    }

    @PostMapping("/{postId}/like")
    public Post likePost(
            @PathVariable Long postId
    ) {

        return postService.likePost(postId);
    }
}
