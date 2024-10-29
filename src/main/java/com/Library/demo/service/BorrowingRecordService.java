package com.Library.demo.service;


import com.Library.demo.entity.BorrowingRecord;
import com.Library.demo.repo.BookRepository;
import com.Library.demo.repo.BorrowingRecordRepository;
import com.Library.demo.repo.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository recordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        BorrowingRecord record = new BorrowingRecord();
        record.setBook(bookRepository.findById(bookId).orElseThrow());
        record.setPatron(patronRepository.findById(patronId).orElseThrow());
        record.setBorrowDate(LocalDate.now());
        return recordRepository.save(record);
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = recordRepository.findByBook_IdAndPatron_IdAndReturnDateIsNull(bookId, patronId);
        record.setReturnDate(LocalDate.now());
        return recordRepository.save(record);
    }
}


