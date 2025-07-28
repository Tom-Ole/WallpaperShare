package com.taskmanager.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.taskmanager.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Custom query methods can be added here if needed
    // For example, to find posts by userId:
    List<Post> findByUserId(Long userId);

    // Or to find posts containing specific content:
    List<Post> findByContentContaining(String keyword);
    
}
