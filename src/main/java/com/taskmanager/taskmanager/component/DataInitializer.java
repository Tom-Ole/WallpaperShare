package com.taskmanager.taskmanager.component;

import java.util.ArrayList;
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

        List<Long> userIds = new ArrayList<Long>();

        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            admin.setFirstname("Admin");
            admin.setLastname("User");
            admin.setEmail("admin@example.com");
            admin.setProfilePictureUrl("http://localhost:4000/get/bdb04cc6-c4b6-42a8-ab76-340e5060edbb.jpg");
            UserEntity savedAdmin = userRepository.save(admin);
            userIds.add(savedAdmin.getId());
            System.out.println("Admin user created with username 'admin' and password 'admin'");

            for (int i = 1; i <= 3; ++i) {
                UserEntity user = new UserEntity();
                user.setUsername("user" + i);
                user.setPassword(passwordEncoder.encode("password"));
                user.setRole("USER");
                user.setFirstname("User" + i);
                user.setLastname("Test" + i);
                user.setEmail("user" + i + "@example.com");
                user.setProfilePictureUrl("http://localhost:4000/get/094d5a60-9546-4cd6-bc08-c7c2ac91d727.jpg");
                UserEntity savedUser = userRepository.save(user);
                userIds.add(savedUser.getId());

            }
            System.out.println("Sample users created.");
        }


        if (postRepository.count() == 0) {

            List<String> testPictures = List.of(
                "http://localhost:4000/get/094d5a60-9546-4cd6-bc08-c7c2ac91d727.jpg",
                "http://localhost:4000/get/25ef3b71-7a43-4111-b8c4-936897aeaa44.jpg",
                "http://localhost:4000/get/5949fee9-efcb-42b4-b371-815ba1c1780e.webp",
                "http://localhost:4000/get/b5e056ba-658c-4fff-8683-7766ae133d90.png",
                "http://localhost:4000/get/bdb04cc6-c4b6-42a8-ab76-340e5060edbb.jpg",
                "http://localhost:4000/get/c49405b6-24dd-4c34-920c-a409919eec6a.jpg",
                "http://localhost:4000/get/d319e024-1789-488c-b426-12644040274c.webp",
                "http://localhost:4000/get/fec17718-a2c6-472e-b885-709308ba2505.webp"
            );


            for (int i = 1; i <= 8; i++) {
                final int currentI = i;
                Post post = new Post();
                post.setContent("Content for post " + currentI);
                post.setCreatedAt(java.time.LocalDateTime.now().plusDays(i));
                post.setImgUrl(testPictures.get(currentI % testPictures.size()));
                post.setLikes(currentI * currentI);
                post.setUserId(userIds.get(i % userIds.size())); // Assigning to one of the created users

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
