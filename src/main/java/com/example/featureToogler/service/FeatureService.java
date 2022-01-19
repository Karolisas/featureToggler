package com.example.featureToogler.service;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.dto.UserFeature;
import com.example.featureToogler.repository.FeatureRepository;
import com.example.featureToogler.repository.UserFeatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    private final UserFeatureRepository userFeatureRepository;

    public FeatureService(FeatureRepository featureRepository, UserFeatureRepository userFeatureRepository) {
        this.featureRepository = featureRepository;
        this.userFeatureRepository = userFeatureRepository;
    }

    public List<Feature> getFeatures() {
        return featureRepository.findAll();
    }

    public Feature createNewFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    @Transactional
    public Feature enableDisableGlobalFeature(Long featureId, boolean isEnabled) {
        return Optional.ofNullable(featureRepository.getById(featureId))
                .map(f -> {
                            f.setEnabledGlobally(isEnabled);
                            return featureRepository.save(f);
                        }
                ).orElse(null);
    }

    public void deleteFeature(Long id) {
        Optional.ofNullable(featureRepository.getById(id))
                .ifPresent(featureRepository::delete);
    }

    public List<Feature> getEnabledGlobalFeatures() {
        return featureRepository.findEnabledFeatures();
    }

    public UserFeature enableUserFeature(Long userId, Long featureId) {
        List<Long> features = Optional.ofNullable(userFeatureRepository.findAllByUserId(userId))
                .map(userFeatures -> userFeatures.stream()
                        .map(UserFeature::getFeatureId)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        UserFeature userFeature = new UserFeature();
        if (!features.contains(featureId)) {
            userFeature = new UserFeature();
            userFeature.setUserId(userId);
            userFeature.setFeatureId(featureId);
            userFeatureRepository.save(userFeature);
        }
        return userFeature;
    }

    public List<UserFeature> getEnabledFeaturesOnlyForUser(Long userId) {
        List<UserFeature> features = new ArrayList<>();
        features.addAll(userFeatureRepository.findAllByUserId(userId));
        return features;
    }

    public List<Feature> getGlobalEnabledAndUserFeatures(Long userId) {
        List<Feature> features = new ArrayList<>();
        features.addAll(getEnabledGlobalFeatures());
        features.addAll(featureRepository.findAllById(
                userFeatureRepository.findAllByUserId(userId).stream()
                        .map(UserFeature::getFeatureId)
                        .collect(Collectors.toSet())));
        return features;
    }

}
