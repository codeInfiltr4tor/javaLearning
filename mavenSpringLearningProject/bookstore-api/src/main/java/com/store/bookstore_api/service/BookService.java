package com.store.bookstore_api.service;

import com.store.bookstore_api.model.book;

import java.util.List;


public interface BookService {

    book saveBook(book bk);
    List<book> getAllBooks();
    book getBookByID(Long Id);
    book updateBook(Long Id, book bk);
    void deleteBook(Long Id);

}
