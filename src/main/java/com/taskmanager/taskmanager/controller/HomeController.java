package com.taskmanager.taskmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public HomeController(PostRepository postRepository, CommentRepository commentRepository,
            UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Comment> comments = commentRepository.findAll();
        List<UserEntity> users = userRepository.findAll();

        Map<Long, UserEntity> userMap = users.stream()
                .collect(Collectors.toMap(UserEntity::getId, user -> user));

        Map<Long, List<Comment>> commentsByPostId = comments.stream()
                .collect(Collectors.groupingBy(Comment::getPostId));

        List<PostDTO> posts = postRepository.findAll().stream()
                .map(post -> {
                    List<CommentDTO> commentDTOs = commentsByPostId
                            .getOrDefault(post.getId(), List.of()).stream()
                            .map(comment -> new CommentDTO(
                                    comment,
                                    userMap.getOrDefault(comment.getUserId(), new UserEntity())))
                            .toList();

                    UserEntity creator = userMap.get(post.getUserId());
                    return new PostDTO(post, commentDTOs, creator);
                })
                .filter(postDTO -> postDTO.getCreator() != null)
                .collect(Collectors.toList());

        // Sort posts by creation date in descending order
        posts.sort((p1, p2) -> p2.getPost().getCreatedAt()
                .compareTo(p1.getPost().getCreatedAt()));

        model.addAttribute("posts", posts);

        return "home";
    }
}
