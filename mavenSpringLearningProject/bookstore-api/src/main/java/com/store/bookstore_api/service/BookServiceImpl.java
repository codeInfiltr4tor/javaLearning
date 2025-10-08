package com.store.bookstore_api.service;

import com.store.bookstore_api.model.book;
import com.store.bookstore_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public book saveBook(book bk) {
        return bookRepository.save(bk);
    }

    @Override
    public List<book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public book getBookByID(Long Id) {
        return bookRepository.findById(Id).orElse(null);
    }

    @Override
    public book updateBook(Long Id, book bk) {
        book bookExist = bookRepository.findById(Id).orElse(null);
        if(bookExist != null){
            bookExist.setTitle(bk.getTitle());
            bookExist.setAuthor(bk.getAuthor());
            bookExist.setPrice(bk.getPrice());
            bookExist.setIsbn(bk.getIsbn());
            return bookRepository.save(bookExist);

        }
        return null;
    }

    @Override
    public void deleteBook(Long Id) {
        bookRepository.deleteById(Id);

    }
}
