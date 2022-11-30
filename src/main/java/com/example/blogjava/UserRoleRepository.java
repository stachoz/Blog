package com.example.blogjava;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<Long, UserRole> {

}
