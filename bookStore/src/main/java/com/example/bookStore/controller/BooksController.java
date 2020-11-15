package com.example.bookStore.controller;

import com.example.bookStore.entity.Book;
import com.example.bookStore.entity.Users;
import com.example.bookStore.repo.BookRepo;
import com.example.bookStore.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("book")
public class BooksController {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @PutMapping("/add/{id}")
    public void addBookInLibrary(@AuthenticationPrincipal User user, @PathVariable("id") String bookId){
        Users users = userRepo.findByUsername(user.getUsername());
        Book book = bookRepo.findById(bookId).get();
        System.out.println();
        users.addBookInLibrary(book);
        userRepo.save(users);
    }

    @GetMapping("/sorted/{id}")
    public List<Book> sortBooks(@PathVariable("id") Integer index) {
        List<Book> sortedBooks = bookRepo.findAll();
        if (index.equals(1)) {
            sortedBooks.sort(Comparator.comparing(Book::getDate));
        }
        if (index.equals(2)) {
            Collections.sort(sortedBooks, new BooksComparator());
        }
        return sortedBooks;
    }

    @GetMapping("{id}")
    public Book getOne(@RequestParam("id") Book book) {
        return book;
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        book.setId(UUID.randomUUID().toString());
        bookRepo.save(book);
        return book;
    }

    @PutMapping("{id}")
    public Book update(@PathVariable("id") Book bookFromDB, @RequestBody Book book) {
        BeanUtils.copyProperties(book, bookFromDB, "id");
        return bookRepo.save(bookFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Book book) {
        bookRepo.delete(book);
    }

}