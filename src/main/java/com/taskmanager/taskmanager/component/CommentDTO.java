package com.taskmanager.taskmanager.component;

import java.time.LocalDateTime;

import com.taskmanager.taskmanager.entity.Comment;
import com.taskmanager.taskmanager.entity.UserEntity;

public class CommentDTO {

    private long id;

    private String content;

    private UserEntity user;

    private Long postId;

    private LocalDateTime createdAt;

    private Integer likes;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment, UserEntity user) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = user;
        this.postId = comment.getPostId();
        this.createdAt = comment.getCreatedAt();
        this.likes = comment.getLikes();
    }

    // Setters and Getters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

}
