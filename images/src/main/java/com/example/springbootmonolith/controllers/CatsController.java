package com.example.springbootmonolith.controllers;


import com.example.springbootmonolith.models.Cats;
import com.example.springbootmonolith.repositories.CatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RestController
public class CatsController {

    @Autowired
    private CatsRepository catsRepository;

    @GetMapping("/cats")
    public Iterable<Cats> findAllcats() {
        return catsRepository.findAll();
    }

    @GetMapping("/cats/{CatId}")
    public Cats findCatById(@PathVariable Long CatId) {
        return catsRepository.findOne(CatId);
    }

    @DeleteMapping("/cats/{CatId}")
    public HttpStatus deleteCatById(@PathVariable Long CatId) {
        catsRepository.delete(CatId);
        return HttpStatus.OK;
    }

    @PostMapping("/cats")
    public Cats createNewCat(@RequestBody Cats newCat) {
        return catsRepository.save(newCat);
    }

    @PatchMapping("/cats/{CatId}")
    public Cats updateDogById(@PathVariable Long CatId, @RequestBody Cats CatRequest) {
        Cats CatFromDb = catsRepository.findOne(CatId);
        CatFromDb.setTitle(CatRequest.getTitle());
        CatFromDb.setImage(CatRequest.getImage());

        return catsRepository.save(CatFromDb);
    }

}

