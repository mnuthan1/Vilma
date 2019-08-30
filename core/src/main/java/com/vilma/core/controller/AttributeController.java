package com.vilma.core.controller;

import java.util.List;

import com.vilma.core.entity.Attribute;
import com.vilma.core.repo.AttributeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/v1/admin/")
@Api(value="Admin API", description="Operations pertaining to attribute admin type")
public class AttributeController {

    @Autowired
    private AttributeRepository attrRepo;

    @GetMapping("/attributes")
    public List<Attribute> getAttributes() {
        return attrRepo.findAll();
    }

    @PostMapping("/attribute")
    public Attribute saveAttribute(@RequestBody Attribute entity) {
        return attrRepo.save(entity);
    }
}