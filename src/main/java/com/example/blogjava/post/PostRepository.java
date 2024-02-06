package com.example.blogjava.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUser_PostVerificationOrderByIdDesc(boolean postVerificationValue, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from post where is_verified = :value")
    List<Post> findAllByVerification(@Param("value") boolean verificationValue);
}
