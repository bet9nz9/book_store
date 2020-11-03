package com.example.bookStore.controller;

import com.example.bookStore.entity.Book;
import com.example.bookStore.repo.BookRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public Book getOne(@RequestParam("id") Book book) {
        return book;
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        String string = UUID.randomUUID().toString();
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

//    private final BookRepo bookRepo;
//
//    @Autowired
//    public BooksController(BookRepo bookRepo) {
//        this.bookRepo = bookRepo;
//    }
//
//    @GetMapping
//    public List<Map<String, String>> getAll(){
//        return null;
//    }
//    @GetMapping("{id}")
//    public Map<String, String> getOne(@PathVariable String id){
//        return null /*books.stream().filter(book -> book.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new)*/;
//    }
//    @PostMapping
//    public Book create(@RequestBody Book book){
//        bookRepo.save(book);
////        book.put("id", String.valueOf(counter));
////        counter++;
////        books.add(book);
//        return book;
//    }
//    @PutMapping("{id}")
//    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> book){
////        Map<String, String> bookFromDB = getOne(id);
////
////        bookFromDB.putAll(book);
////        bookFromDB.put("id", id);
//        return null /*bookFromDB*/;
//    }
//    @DeleteMapping("{id}")
//    public void delete(@PathVariable String id){
////        books.remove(getOne(id));