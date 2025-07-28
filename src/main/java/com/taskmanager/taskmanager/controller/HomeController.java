package com.taskmanager.taskmanager.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.taskmanager.taskmanager.component.PostDTO;
import com.taskmanager.taskmanager.entity.Comment;
import com.taskmanager.taskmanager.entity.UserEntity;
import com.taskmanager.taskmanager.repository.CommentRepository;
import com.taskmanager.taskmanager.repository.PostRepository;
import com.taskmanager.taskmanager.repository.UserRepository;


@Controller
public class HomeController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public HomeController(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;   
    }

    @GetMapping("/")
    public String home(Model model) {

        List<PostDTO> posts = postRepository.findAll().stream()
            .map(post -> {
                List<Comment> comments = commentRepository.findByPostId(post.getId());
                UserEntity creator = userRepository.findById(post.getUserId()).orElse(null);
                return new PostDTO(post, comments, creator);
            })
            .toList();

        model.addAttribute("posts", posts);

        return "home";
    }
}
