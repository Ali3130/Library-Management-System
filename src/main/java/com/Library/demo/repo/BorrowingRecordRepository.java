package com.Library.demo.repo;

import com.Library.demo.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    BorrowingRecord findByBook_IdAndPatron_IdAndReturnDateIsNull(Long bookId, Long patronId);
}
