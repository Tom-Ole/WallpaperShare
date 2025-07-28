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

            List<Comment> comments = List.of(
                    new Comment("This is a comment on post 1", 1L, 1L, 100),
                    new Comment("This is a comment on post 2", 1L, 2L, 1),
                    new Comment("This is a comment on post 3", 1L, 3L, 1),
                    new Comment("This is a comment on post 4", 1L, 4L, 1),
                    new Comment("This is a comment on post 5", 1L, 5L, 1),
                    new Comment("This is a comment on post 1 the second", 1L, 1L, 1),
                    new Comment("This is a comment on post 1 the third", 1L, 1L, 10)
            // new Comment(1L, "This is a comment on post 1", 1L, 1L, 100),
            // new Comment(2L,"This is a comment on post 2", 1L, 2L, 1),
            // new Comment(3L,"This is a comment on post 3", 1L, 3L, 1),
            // new Comment(4L,"This is a comment on post 4", 1L, 4L, 1),
            // new Comment(5L,"This is a comment on post 5", 1L, 5L, 1),
            // new Comment(6L,"This is a comment on post 1 the second", 1L, 1L, 1),
            // new Comment(7L,"This is a comment on post 1 the third", 1L, 1L, 10)
            );

            commentRepository.saveAll(comments);

            for (int i = 1; i <= 5; i++) {
                final int currentI = i;
                Post post = new Post();
                post.setContent("Content for post " + currentI);
                post.setCreatedAt(java.time.LocalDateTime.now());
                post.setImgUrl("https://example.com/image" + currentI + ".jpg");
                post.setLikes(currentI * currentI);
                post.setUserId(1L);

                Post savedPost = postRepository.save(post); // Speichern und ID generieren lassen

                // Jetzt zugeordnete Comments speichern
                List<Comment> commentsForPost = comments.stream()
                        .filter(comment -> comment.getPostId() == (long) currentI)
                        .peek(comment -> comment.setPostId(savedPost.getId()))
                        .toList();

                commentRepository.saveAll(commentsForPost);

                // IDs der Comments als Longs speichern
                savedPost.setComments(commentsForPost.stream()
                        .map(Comment::getId)
                        .toList());

                postRepository.save(savedPost); // Aktualisieren mit zugeordneten Comments
            }
            System.out.println("Sample posts created.");
        }
    }
}
