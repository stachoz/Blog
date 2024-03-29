package com.example.blogjava.user.repos;

import com.example.blogjava.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findAllByUserRoles_RoleName(String userRole);
    Optional<User> findByUsername(String username);
    boolean existsUserByEmail(String email);
    boolean existsUserByUsername(String username);
    void deleteByUsername(String username);

    /**
     * Check if table has any content
     * @return 1 if first row exists otherwise 0
     */
    @Query(nativeQuery = true, value = "select exists(select 1 from application_user limit 1)")
    Long hasAnyRows();

    @Query(nativeQuery = true, value = "select count(p.id) from application_user u join post p on u.id = p.author_id where u.username = :u")
    int countUserPosts(@Param("u") String username);



}