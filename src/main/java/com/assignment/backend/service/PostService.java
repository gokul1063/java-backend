package com.assignment.backend.service;

import com.assignment.backend.dto.CreateCommentRequest;
import com.assignment.backend.dto.CreatePostRequest;
import com.assignment.backend.entity.Bot;
import com.assignment.backend.entity.Comment;
import com.assignment.backend.entity.Post;
import com.assignment.backend.entity.User;
import com.assignment.backend.entity.enums.AuthorType;
import com.assignment.backend.exception.BadRequestException;
import com.assignment.backend.exception.ResourceNotFoundException;
import com.assignment.backend.repository.BotRepository;
import com.assignment.backend.repository.CommentRepository;
import com.assignment.backend.repository.PostRepository;
import com.assignment.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BotRepository botRepository;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       UserRepository userRepository,
                       BotRepository botRepository) {

        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.botRepository = botRepository;
    }

    public Post createPost(CreatePostRequest request) {

        validateAuthor(
                request.getAuthorType(),
                request.getAuthorId()
        );

        Post post = new Post();

        post.setAuthorType(request.getAuthorType());
        post.setAuthorId(request.getAuthorId());
        post.setContent(request.getContent());
        post.setLikesCount(0);
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public Comment createComment(Long postId,
                                 CreateCommentRequest request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found")
                );

        validateAuthor(
                request.getAuthorType(),
                request.getAuthorId()
        );

        if (request.getDepthLevel() < 0) {
            throw new BadRequestException(
                    "Depth level cannot be negative"
            );
        }

        Comment comment = new Comment();

        comment.setPostId(post.getId());
        comment.setAuthorType(request.getAuthorType());
        comment.setAuthorId(request.getAuthorId());
        comment.setContent(request.getContent());
        comment.setDepthLevel(request.getDepthLevel());
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public Post likePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found")
                );

        post.setLikesCount(post.getLikesCount() + 1);

        return postRepository.save(post);
    }

    private void validateAuthor(AuthorType authorType,
                                Long authorId) {

        if (authorType == AuthorType.USER) {

            User user = userRepository.findById(authorId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found"
                            )
                    );

        } else {

            Bot bot = botRepository.findById(authorId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Bot not found"
                            )
                    );
        }
    }
}
