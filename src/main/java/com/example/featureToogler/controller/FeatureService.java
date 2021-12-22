package com.example.featureToogler.controller;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void editFeature(Long id, boolean isEnabled) {
        Optional.ofNullable(repository.getById(id))
                .map(feature -> feature.setEnabled(isEnabled))
                .map(a->repository.save(a))
                .orElseThrow();
    }

    public void deleteFeature(Long id) {
        Optional.ofNullable(repository.getById(id))
                .ifPresent(a -> repository.delete(a));
    }

    public List<Feature> getEnabledFeatures() {
        return repository.findAll().stream()
                .filter(Feature::isEnabled)
                .collect(Collectors.toList());
    }
}
