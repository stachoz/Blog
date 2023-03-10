package com.example.blogjava.user.repos;

import com.example.blogjava.user.User;
import org.springframework.data.repository.CrudRepository;
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

}