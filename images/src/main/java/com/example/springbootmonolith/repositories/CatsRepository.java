package com.example.springbootmonolith.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.springbootmonolith.models.Cats;


public interface CatsRepository extends CrudRepository<Cats, Long> {
}

