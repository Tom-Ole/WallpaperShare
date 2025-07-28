package com.taskmanager.taskmanager.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.taskmanager.taskmanager.component.PostDTO;
import com.taskmanager.taskmanager.component.CommentDTO;
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
        List<Comment> comments = commentRepository.findAll();

        
        // Use the postId from the Comment to find all comments to the related post for all Posts.


        List<PostDTO> posts = postRepository.findAll().stream()
            .map(post -> {
                List<CommentDTO> commentDTOs = comments.stream()
                    .filter(comment -> Objects.equals(comment.getPostId(), post.getId()))
                    .map(comment -> new CommentDTO(comment, userRepository.findById(comment.getUserId()).orElse(new UserEntity())))
                    .toList();

                UserEntity creator = userRepository.findById(post.getUserId()).orElse(null);
                return new PostDTO(post, commentDTOs, creator);
            }).toList();

        // Remove all Posts that have no creator assigned
        posts.removeIf(postDTO -> postDTO.getCreator() == null);

        model.addAttribute("posts", posts);

        return "home";
    }
}
