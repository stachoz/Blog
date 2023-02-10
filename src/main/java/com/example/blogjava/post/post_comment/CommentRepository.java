package com.example.blogjava.post.post_comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {
    List<Comment> findAllByPost_IdOrderByIdDesc(Long postId);
}
