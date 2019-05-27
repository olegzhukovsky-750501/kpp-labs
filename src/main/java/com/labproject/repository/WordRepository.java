package com.labproject.repository;

import com.labproject.entity.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {
    List<Word> findAll();
    List<Word> getAllById(int id);
}
