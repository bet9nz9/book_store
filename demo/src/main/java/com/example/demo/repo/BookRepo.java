package com.example.demo.repo;

import com.example.demo.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepo extends MongoRepository<Book, Long> {
}
