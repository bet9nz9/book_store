package com.example.bookStore.controller;

import com.example.bookStore.entity.Book;
import com.example.bookStore.entity.Users;
import com.example.bookStore.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("library")
public class LibraryController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<Book> getUserLibrary(@AuthenticationPrincipal User user){
        Users users = userRepo.findByUsername(user.getUsername());
        return users.getUserLibrary();
    }

    @GetMapping("/payment")
    public List<Book> payment(@AuthenticationPrincipal User user){
        Users users = userRepo.findByUsername(user.getUsername());
        users.clearLibrary();
        userRepo.save(users);
        return users.getUserLibrary();
    }

    @GetMapping("/sum")
    public String sumOfLibrary(@AuthenticationPrincipal User user){
        int sum = 0;
        Users users = userRepo.findByUsername(user.getUsername());
        List<Book> library = new ArrayList<>(users.getUserLibrary());
        for (Book book : library){
            sum+= Integer.valueOf(book.getCoast());
        }
        return String.valueOf(sum);
    }

    @GetMapping("/sorted/{id}")
    public List<Book> sortBooks(@AuthenticationPrincipal User user, @PathVariable("id") Integer index) {
        Users users = userRepo.findByUsername(user.getUsername());
        List<Book> library = new ArrayList<>(users.getUserLibrary());
        if (index.equals(1)) {
            library.sort(Comparator.comparing(Book::getDate));
        }
        if (index.equals(2)) {
            Collections.sort(library, new BooksComparator());
        }
        return library;
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal User user, @PathVariable("id") Book book){
        Users users = userRepo.findByUsername(user.getUsername());

        for (int i =0; i<users.getUserLibrary().size();i++){
            if (users.getUserLibrary().get(i).equals(book)){
                users.getUserLibrary().remove(i);
                break;
            }
        }

        userRepo.save(users);
    }
}
