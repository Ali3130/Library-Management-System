package com.Library.demo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.Library.demo.controller.BookController;
import com.Library.demo.entity.Book;
import com.Library.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.Arrays;


public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks_ShouldReturnBooks() throws Exception {
        Book book1 = new Book(1L, "Book Title 1", "Author 1", 2020, "1234567890");
        Book book2 = new Book(2L, "Book Title 2", "Author 2", 2021, "0987654321");

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getBookById_ShouldReturnBook_WhenBookExists() throws Exception {
        Book book = new Book(1L, "Book Title", "Author", 2020, "1234567890");

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book Title"));
    }

    @Test
    public void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addBook_ShouldReturnCreatedBook() throws Exception {
        Book book = new Book(null, "New Book", "New Author", 2023, "1122334455");

        when(bookService.saveBook(any(Book.class))).thenReturn(new Book(1L, "New Book", "New Author", 2023, "1122334455"));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Book\", \"author\": \"New Author\", \"publicationYear\": 2023, \"isbn\": \"1122334455\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void updateBook_ShouldReturnUpdatedBook() throws Exception {
        Book book = new Book(1L, "Updated Book", "Updated Author", 2023, "1122334455");

        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Book\", \"author\": \"Updated Author\", \"publicationYear\": 2023, \"isbn\": \"1122334455\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    public void deleteBook_ShouldReturnNoContent() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}

