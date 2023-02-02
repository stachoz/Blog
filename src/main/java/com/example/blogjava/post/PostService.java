package com.example.blogjava.post;

import com.example.blogjava.comment.Comment;
import com.example.blogjava.comment.CommentRepository;
import com.example.blogjava.comment.dto.CommentDto;
import com.example.blogjava.comment.dto.CommentDtoMapper;
import com.example.blogjava.comment.dto.CommentFormDto;
import com.example.blogjava.comment.dto.CommentFormDtoMapper;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository, CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
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

    @Transactional
    public PostDto getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(PostDtoMapper::map)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void saveComment(CommentFormDto dto, Long postId){
        Comment comment = CommentFormDtoMapper.map(dto);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.save(userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new));
        comment.setUser(user);
        Post post = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        comment.setPost(post);
        commentRepository.save(comment);
    }
    @Transactional
    public List<CommentDto> getAllComments(Long postId){
        return commentRepository.findAllByPost_IdOrderByIdDesc(postId).stream()
                .map(CommentDtoMapper::map).toList();
    }






}
