package com.example.featureToogler.controller;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    private final FeatureRepository repository;

    private final UserFeatureRepository userFeatureRepository;


    public FeatureService(FeatureRepository featureRepository, UserFeatureRepository userFeatureRepository) {
        this.repository = featureRepository;
        this.userFeatureRepository = userFeatureRepository;
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
                .map(a -> repository.save(a))
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

    public void enableUserFeature(Long userId, Long featureId) {
        Optional.ofNullable(userFeatureRepository.findAllByUserId(userId))
                .map(list -> !list.contains(featureId))//todo fix
                .ifPresent(a -> {
                    UserFeature userFeature = new UserFeature();
                    userFeature.setUserId(userId);
                    userFeature.setFeatureId(featureId);
                    userFeatureRepository.save(userFeature);
                });

    }

    public List<UserFeature> getEanbledFeaturesForUser(Long userId) {
        return userFeatureRepository.findAllByUserId(userId);
    }

}
