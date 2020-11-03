package com.example.bookStore.repo;

import com.example.bookStore.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, Long>{

}
