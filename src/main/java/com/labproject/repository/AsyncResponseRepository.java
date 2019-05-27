package com.labproject.repository;

import com.labproject.entity.AsyncResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AsyncResponseRepository extends CrudRepository<AsyncResponse, Long> {
    List<AsyncResponse> findByResponseId(String id);
}
