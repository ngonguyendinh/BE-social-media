package com.example.mxh.map;

import com.example.mxh.form.FormCreatePost;
import com.example.mxh.model.post.Post;
import com.example.mxh.model.post.PostDto;

import java.util.ArrayList;
import java.util.List;

public class PostMapper {
    public static PostDto map(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setCaption(post.getCaption());
        dto.setImage(post.getImage());
        dto.setVideo(post.getVideo());
        dto.setUser(UserMapper.map(post.getUser()));
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setLiked(UserMapper.map(post.getLike()));

        return dto;
    }
    public static Post map(FormCreatePost form){
        Post post = new Post();
        post.setCaption(form.getCaption());
        post.setImage(form.getImage());
        post.setVideo(form.getVideo());
        return post;
    }
    public static List<PostDto> map(List<Post> posts){
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = map(post);
            postDtos.add(postDto);
        }
        return postDtos;
    }
}
