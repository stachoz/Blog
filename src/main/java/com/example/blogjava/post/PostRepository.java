package com.example.blogjava.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>, CrudRepository<Post, Long> {
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);
    Optional<Post> findTopByOrderByIdDesc();
}
