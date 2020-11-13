package com.example.bookStore.repo;

import com.example.bookStore.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<Users, String> {
    Users findByUsername(String username);
}
