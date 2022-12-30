package com.example.blogjava.post;

import com.example.blogjava.post.dto.PostFormDto;
import com.example.blogjava.post.dto.PostFormDtoMapper;
import com.example.blogjava.user.User;
import com.example.blogjava.user.exception.UserNotFoundException;
import com.example.blogjava.user.repos.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void savePost(PostFormDto postFormDto){
        Post post = PostFormDtoMapper.map(postFormDto);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
        post.setUser(user);
        postRepository.save(post);
    }
}
