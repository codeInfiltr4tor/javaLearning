package com.store.bookstore_api.controller;

import com.store.bookstore_api.model.book;
import com.store.bookstore_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public book createBook(@RequestBody book bk){
        return bookService.saveBook(bk);
    }

    // get all book
    @GetMapping
    public List<book> getAllBooks(){
        return bookService.getAllBooks();
    }

    // get Book by ID
    @GetMapping("/{ID}")
    public book getBookByID(@PathVariable Long ID){
        return bookService.getBookByID(ID);
    }

    @PutMapping("/{ID}")
    public book updateBookbyID(@PathVariable Long ID,@RequestBody book bk){
        return bookService.updateBook(ID, bk);
    }

    @DeleteMapping("/{ID}")
    public String deleteBookByID(@PathVariable Long ID){
        bookService.deleteBook(ID);
        return "Book Deleted Successfully.";
    }


}
