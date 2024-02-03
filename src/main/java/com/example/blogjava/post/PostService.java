package com.example.blogjava.post;

import com.example.blogjava.post.dto.PostDto;
import com.example.blogjava.post.dto.PostDtoMapper;
import com.example.blogjava.post.dto.PostFormDto;
import com.example.blogjava.post.dto.PostFormDtoMapper;
import com.example.blogjava.post.post_comment.*;
import com.example.blogjava.post.report.*;
import com.example.blogjava.user.User;
import com.example.blogjava.user.exception.UserNotFoundException;
import com.example.blogjava.user.repos.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final String BLOCKED_USER_AUTHORITY = "ROLE_BLOCKED_USER";
    private final String ADMIN_AUTHORITY = "ROLE_ADMIN";

    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       CommentRepository commentRepository, ReportRepository reportRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public Page<PostDto> getPageOfPosts(PageRequest pr){
        Page<Post> all = postRepository.findAllByUser_PostVerificationOrderByIdDesc(false,pr);
        return all.map(PostDtoMapper::map);
    }

    @Transactional
    public Optional<String> savePost(PostFormDto postFormDto){
        if (!hasCurrentUserAuthority(BLOCKED_USER_AUTHORITY)){
            Post post = PostFormDtoMapper.map(postFormDto);
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
            post.setUser(user);
            postRepository.save(post);
        }
        if (getCurrentUser().getPostVerification()) return Optional.of("you have to wait for post verification");
        return Optional.empty();
    }

    @Transactional
    public PostDto getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(PostDtoMapper::map)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void saveComment(CommentFormDto dto, Long postId){
        if(!hasCurrentUserAuthority(BLOCKED_USER_AUTHORITY)){
            User currentUser = getCurrentUser();
            Comment comment = new Comment();
            comment.setContent(dto.getContent());
            comment.setUser(currentUser);
            postRepository.findById(postId).ifPresentOrElse(
                    post -> {
                        comment.setPost(post);
                        post.getComments().add(comment);
                        commentRepository.save(comment);
                        postRepository.save(post);
                    },
                    () -> {
                        throw new NoSuchElementException();
                    }
            );
        }
    }

    @Transactional
    public List<CommentDto> getPostComments(Long postId){
        return commentRepository.findAllByPost_IdOrderByIdDesc(postId).stream()
                .map(CommentDtoMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(Long postId){
        if(hasCurrentUserAuthority(ADMIN_AUTHORITY)){
            postRepository.deleteById(postId);
        }
    }

    @Transactional
    public void saveReport(ReportFormDto reportFormDto, Long postId){
        if(!hasCurrentUserAuthority(BLOCKED_USER_AUTHORITY)){
            Report report = ReportFormDtoMapper.map(reportFormDto);
            postRepository.findById(postId).ifPresent(
                    post -> {
                        report.setPost(post);
                        reportRepository.save(report);
                    }
            );
        }
    }

    @Transactional
    public List<ReportAdminDto> getAllReports(){
        List<ReportAdminDto> reports = new ArrayList<>();
        reportRepository.findAll().forEach(
                report -> reports.add(ReportAdminDtoMapper.map(report))
        );
        return reports;
    }

    private boolean hasCurrentUserAuthority(String authority){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }

    private User getCurrentUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(name).orElseThrow(NoSuchElementException::new);
    }
}
