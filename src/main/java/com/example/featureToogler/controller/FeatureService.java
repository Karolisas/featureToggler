package com.example.featureToogler.controller;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

    private final FeatureRepository repository;

    public FeatureService(FeatureRepository featureRepository) {
        this.repository = featureRepository;
    }

    public List<Feature> getFeatures() {
        return repository.findAll();
    }

    public void createNewFeature(Feature feature) {
        repository.save(feature);
    }
}
