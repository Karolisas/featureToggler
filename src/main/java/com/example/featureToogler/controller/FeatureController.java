package com.example.featureToogler.controller;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feature")
public class FeatureController {

    @Autowired
    private FeatureService service;

    @GetMapping
    public List<Feature> getAllFeatures() {
        return service.getFeatures();
    }

    @PostMapping
    public void createFeature() {
        service.createNewFeature(new Feature());
    }

    @GetMapping("/userFeature")
    public String userFeature(){
        return "userFeature accesed";
    }
    @GetMapping("/adminFeature")
    public String adminFeature(){
        return "adminFeature accesed";
    }
}
