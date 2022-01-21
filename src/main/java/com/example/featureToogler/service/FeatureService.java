package com.example.featureToogler.service;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.repository.FeatureRepository;
import com.example.featureToogler.repository.UserFeatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository, UserFeatureRepository userFeatureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> getFeatures() {
        return featureRepository.findAll();
    }

    public Feature createNewFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    /**
     * Feature is toggled On/Off for all users (globally).
     *
     * @param featureId The unique Feature id
     * @param isEnabled true to enable, false to disable
     * @return Feature
     * @throws javax.persistence.EntityNotFoundException if feature does not exist
     */
    @Transactional
    public Feature setEnabledGlobalFeature(Long featureId, boolean isEnabled) {
        return Optional.ofNullable(featureRepository.getById(featureId))
                .map(f -> {
                            f.setEnabledGlobally(isEnabled);
                            return featureRepository.save(f);
                        }
                ).orElseThrow(EntityNotFoundException::new);
    }

    public List<Feature> getEnabledGlobalFeatures() {
        return featureRepository.findEnabledFeatures().orElse(Collections.emptyList());
    }

    public void deleteFeature(Long id) {
        Optional.ofNullable(featureRepository.getById(id))
                .ifPresent(featureRepository::delete);
    }

    public List<Feature> findFeaturesById(Set<Long> ids) {
        return featureRepository.findAllById(ids);
    }


}
