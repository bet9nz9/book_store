package com.example.bookStore.controller;

import com.example.bookStore.entity.Book;
import com.example.bookStore.repo.BookRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("book")
public class BooksController {
    private final BookRepo bookRepo;

    @Autowired
    public BooksController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @GetMapping("/sorted/{id}")
    public List<Book> sortedByDate(@PathVariable("id") Integer index) {
        List<Book> sortedBooks = bookRepo.findAll();
        if (index.equals(1)) {
            sortedBooks.sort(Comparator.comparing(Book::getDate));
        }
        if (index.equals(2)) {
            Collections.sort(sortedBooks, new ByCoastCompare());
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

    private class ByCoastCompare implements Comparator<Book> {
        @Override
        public int compare(Book o1, Book o2) {
            if (o1 == null || o2 == null) return 0;
            Integer firstCoast = new Integer(o1.getCoast());
            Integer secondCoast = new Integer(o2.getCoast());
            if (firstCoast > secondCoast) return 1;
            if (firstCoast < secondCoast) return -1;
            if (firstCoast == secondCoast) return 0;
            return 0;
        }
    }
}