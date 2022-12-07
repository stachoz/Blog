package com.example.blogjava;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<HomeController.User, Long>{
    Optional<HomeController.User> findByUsername(String username);
}