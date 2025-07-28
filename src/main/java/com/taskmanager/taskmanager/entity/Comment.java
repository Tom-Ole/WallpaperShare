package com.taskmanager.taskmanager.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    private String content;

    private Long userId;

    private Long postId;

    private LocalDateTime createdAt;

    private Integer likes;


    public Comment() {
    }

    public Comment(String content, Long userId, Long postId, Integer likes) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = LocalDateTime.now();
        this.likes = likes;
    }

    public Comment(long id, String content, Long userId, Long postId, Integer likes) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = LocalDateTime.now();
        this.likes = likes;
    }

    // Getters and Setters
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", postId=" + postId +
                ", createdAt=" + createdAt +
                ", likes=" + likes +
                '}';
    }
    

}
