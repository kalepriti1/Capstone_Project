package com.natwest.CapstoneUserService.repository;

import com.natwest.CapstoneUserService.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByEmail(String email);
}
