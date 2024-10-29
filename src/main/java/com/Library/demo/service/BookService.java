package com.Library.demo.service;


import com.Library.demo.entity.Book;
import com.Library.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public Book getBookById(Long id) { return bookRepository.findById(id).orElse(null); }
    @Transactional
    public Book saveBook(Book book) { return bookRepository.save(book); }
    @Transactional
    public void deleteBook(Long id) { bookRepository.deleteById(id); }

}
