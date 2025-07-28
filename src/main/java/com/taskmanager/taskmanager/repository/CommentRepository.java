package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Custom query methods can be added here if needed
    // For example, to find comments by postId:
    List<Comment> findByPostId(Long postId);

    // Or to find comments by userId:
    List<Comment> findByUserId(Long userId);
    
}
