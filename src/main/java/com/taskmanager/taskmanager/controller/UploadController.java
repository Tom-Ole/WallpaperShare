package com.taskmanager.taskmanager.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.aspectj.apache.bcel.generic.TABLESWITCH;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.taskmanager.taskmanager.component.UploadResponse;
import com.taskmanager.taskmanager.entity.AuthUser;
import com.taskmanager.taskmanager.entity.Post;
import com.taskmanager.taskmanager.repository.PostRepository;

@Controller
public class UploadController {

    PostRepository postRepository;

    public UploadController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            @RequestParam("content") String content) {

        // upload file to my own s3Clone on http://localhost:4000 via a HTTP Post
        // request.
        // Assuming the file is uploaded successfully and we get the file name back.
        String fileName;
        try {
            fileName = uploadToS3Clone(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/upload?error"; // Redirect with error if upload fails
        }

        Post post = new Post();
        post.setContent(content);
        post.setImgUrl(fileName);
        post.setLikes(0);

        // Get current authorized user ID
        Long userID = getCurrentUserId();
        post.setUserId(userID);

        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);

        return "redirect:/";
    }

    private String uploadToS3Clone(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer secrettoken");

        ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<UploadResponse> response = restTemplate.postForEntity("http://localhost:4000/upload",
                requestEntity,
                UploadResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            UploadResponse uploadResponse = response.getBody();
            if (uploadResponse == null) {
                throw new RuntimeException("Failed to upload file: No response body");
            } else {
                return uploadResponse.getUrl();
            }
        } else {
            throw new RuntimeException("Failed to upload file: " + response.getStatusCode());
        }

    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
            return null;

        AuthUser user = (AuthUser) auth.getPrincipal();
        return user.getId();
    }

}
