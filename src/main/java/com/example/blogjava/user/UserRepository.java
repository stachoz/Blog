package com.example.blogjava.user;

import com.example.blogjava.HomeController;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findAllByUserRoles_RoleName(String userRole);
    Optional<User> findByUsername(String username);

}