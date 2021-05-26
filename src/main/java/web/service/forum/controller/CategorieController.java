package web.service.forum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import web.service.forum.entity.Categorie;
import web.service.forum.repository.CategorieRepository;


@RestController
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;


    @ResponseBody
    @GetMapping("/categorie")
    public Page<Categorie> getCategorie(Pageable pageable) {
        return categorieRepository.findAll(pageable);
    }

    @ResponseBody
    @GetMapping("/categorie/{id}")
    public Categorie getCategorieById(final @PathVariable("id") String categorieId) {
        try {
            return categorieRepository.findById(Integer.valueOf(categorieId)).get();
        } catch (Exception e) {
            return null;
        }
    }



}
