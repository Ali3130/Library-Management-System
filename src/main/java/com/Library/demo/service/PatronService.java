package com.Library.demo.service;


import com.Library.demo.entity.Patron;
import com.Library.demo.repo.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatronService {

    @Autowired
    PatronRepository patronRepository;


    public List<Patron> getAllPatrons()
    {
        return patronRepository.findAll();
    }
    public Patron getPatronById(Long id)

    {
        return patronRepository.findById(id).orElse(null);
    }
    @Transactional
    public Patron savePatron(Patron patron)

    {
        return patronRepository.save(patron);
    }
    @Transactional
    public void deletePatron(Long id)
    {
        patronRepository.deleteById(id);
    }

}
