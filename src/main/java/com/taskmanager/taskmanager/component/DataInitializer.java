package com.taskmanager.taskmanager.component;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.taskmanager.taskmanager.entity.Post;
import com.taskmanager.taskmanager.entity.Comment;
import com.taskmanager.taskmanager.entity.UserEntity;
import com.taskmanager.taskmanager.repository.CommentRepository;
import com.taskmanager.taskmanager.repository.PostRepository;
import com.taskmanager.taskmanager.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
            PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            admin.setFirstname("Admin");
            admin.setLastname("User");
            admin.setEmail("admin@example.com");
            userRepository.save(admin);
            System.out.println("Admin user created with username 'admin' and password 'admin'");
        }

        if (postRepository.count() == 0) {


            for (int i = 1; i <= 5; i++) {
                final int currentI = i;
                Post post = new Post();
                post.setContent("Content for post " + currentI);
                post.setCreatedAt(java.time.LocalDateTime.now());
                post.setImgUrl("https://example.com/image" + currentI + ".jpg");
                post.setLikes(currentI * currentI);
                post.setUserId(1L);

                Post savedPost = postRepository.save(post);

                // Assign comment / comments to the post

                for (int j = 1; j <= Math.ceil(Math.random() * 10); j++) {
                    Comment comment = new Comment();
                    comment.setContent("Comment " + j + " for post " + currentI);
                    comment.setCreatedAt(java.time.LocalDateTime.now());
                    comment.setLikes(j);
                    comment.setPostId(savedPost.getId());
                    comment.setUserId(1L); // Assuming the admin user is the commenter
                    commentRepository.save(comment);
                }

               
            }
            System.out.println("Sample posts created.");
        }
    }
}
