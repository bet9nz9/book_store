package com.example.bookStore.controller;

import com.example.bookStore.entity.Book;

import java.util.Comparator;

class BooksComparator implements Comparator<Book> {
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
