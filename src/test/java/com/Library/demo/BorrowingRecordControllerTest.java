package com.Library.demo;




import com.Library.demo.controller.BorrowingRecordController;
import com.Library.demo.entity.BorrowingRecord;
import com.Library.demo.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowingRecordControllerTest {

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook() {
        Long bookId = 1L;
        Long patronId = 1L;
        BorrowingRecord mockRecord = new BorrowingRecord();
        mockRecord.setId(1L);
        mockRecord.setBorrowDate(LocalDate.now());

        when(borrowingRecordService.borrowBook(bookId, patronId)).thenReturn(mockRecord);

        ResponseEntity<BorrowingRecord> response = borrowingRecordController.borrowBook(bookId, patronId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRecord, response.getBody());
        verify(borrowingRecordService, times(1)).borrowBook(bookId, patronId);
    }

    @Test
    void testReturnBook() {
        Long bookId = 1L;
        Long patronId = 1L;
        BorrowingRecord mockRecord = new BorrowingRecord();
        mockRecord.setId(1L);
        mockRecord.setReturnDate(LocalDate.now());

        when(borrowingRecordService.returnBook(bookId, patronId)).thenReturn(mockRecord);

        ResponseEntity<BorrowingRecord> response = borrowingRecordController.returnBook(bookId, patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRecord, response.getBody());
        verify(borrowingRecordService, times(1)).returnBook(bookId, patronId);
    }
}
