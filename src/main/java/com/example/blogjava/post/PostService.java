package com.example.blogjava.post;

import com.example.blogjava.post.dto.PostDto;
import com.example.blogjava.post.dto.PostDtoMapper;
import com.example.blogjava.post.dto.PostFormDto;
import com.example.blogjava.post.dto.PostFormDtoMapper;
import com.example.blogjava.user.User;
import com.example.blogjava.user.exception.UserNotFoundException;
import com.example.blogjava.user.repos.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Page<PostDto> getPageOfPosts(PageRequest pr){
        Page<Post> all = postRepository.findAllByOrderByIdDesc(pr);
        Page<PostDto> map = all.map(PostDtoMapper::map);
        return map;
    }

    @Transactional
    public void savePost(PostFormDto postFormDto){
        Post post = PostFormDtoMapper.map(postFormDto);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
        post.setUser(user);
        postRepository.save(post);
    }


}
