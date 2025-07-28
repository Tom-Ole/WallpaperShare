package com.taskmanager.taskmanager.component;

import com.taskmanager.taskmanager.entity.Post;
import com.taskmanager.taskmanager.entity.UserEntity;

import java.util.List;


public class PostDTO {

    private Post post;
    private List<CommentDTO> comments;
    private UserEntity creator;

    public PostDTO(Post post, List<CommentDTO> comments, UserEntity creator) {
        this.post = post;
        this.comments = comments;
        this.creator = creator;
    }

    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public List<CommentDTO> getComments() {
        return comments;
    }
    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
    public UserEntity getCreator() {
        return creator;
    }
    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

}
