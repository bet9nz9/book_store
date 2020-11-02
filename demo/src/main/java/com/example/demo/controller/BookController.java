package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.repo.BookRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("book")
public class BookController {
    private final BookRepo bookRepo;

    @Autowired
    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookRepo.save(book);
    }

    @PutMapping("{id}")
    public Book update(@PathVariable("id") Book bookFromDB,
                       @RequestBody Book book) {
        BeanUtils.copyProperties(book, bookFromDB, "id");
        return bookRepo.save(bookFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Book book) {
        bookRepo.delete(book);
    }
}
