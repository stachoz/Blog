package com.example.blogjava.user;

import com.example.blogjava.config.CustomUserDetailsService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<CustomUserDetailsService.UserRole, Long>{
}