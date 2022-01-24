package com.example.featureToogler.service;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.repository.FeatureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class FeatureServiceTest {

    public static final long USER_ID = 1L;
    @Autowired
    FeatureService featureService;

    @Autowired
    FeatureRepository featureRepository;

    private Feature feature1;
    private Feature feature2;

    @BeforeEach
    public void setUp() {
        feature1 = new Feature();
        feature2 = new Feature();
        featureService.createNewFeature(feature1);
        featureService.createNewFeature(feature2);
    }

    @AfterEach
    public void deleteTableValues() {
        featureRepository.deleteAll();
    }

    @Test
    public void repositoriesInitialized() {
        Assertions.assertNotNull(featureRepository);
    }

    @Test
    public void getFeatures() {
        Assertions.assertArrayEquals(List.of(feature1, feature2).toArray(), featureService.getFeatures().toArray());
        Assertions.assertEquals(List.of(feature1, feature2), featureService.getFeatures());
    }

    @Test
    public void createNewFeature_defaultDisabledTest() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());
        Assertions.assertEquals(false, featureService.getFeatures().stream()
                .findFirst()
                .map(Feature::isEnabledGlobally)
                .get());
    }

    @Test
    public void editFeature_enableGlobalFeature() {
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());
        Assertions.assertEquals(true, featureService.setEnabledGlobalFeature(feature1.getId(), true).isEnabledGlobally());
        Assertions.assertEquals(1, featureService.getEnabledGlobalFeatures().size());
        Assertions.assertEquals(feature1.getId(), featureService.getEnabledGlobalFeatures()
                .stream()
                .map(Feature::getId)
                .filter(id -> id.equals(feature1.getId()))
                .findFirst()
                .orElse(null));

        featureService.setEnabledGlobalFeature(feature1.getId(), false);
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());
    }

    @Test
    public void editFeature_whichDoesNotExist() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertThrows(EntityNotFoundException.class, () -> featureService.setEnabledGlobalFeature(33L, true));
    }

    @Test
    public void deleteFeature_Test() {
        Feature feature = new Feature();
        featureService.createNewFeature(feature);
        Assertions.assertEquals(feature, featureService.getFeatures().stream()
                .filter(f -> f.equals(feature))
                .findFirst()
                .orElse(null));

        featureService.deleteFeature(feature.getId());
        Assertions.assertFalse(featureService.getFeatures().contains(feature));
    }

    @Test
    public void getEnabledFeaturesTest() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());

        featureService.setEnabledGlobalFeature(feature1.getId(), true);
        Assertions.assertEquals(1, featureService.getEnabledGlobalFeatures().size());
    }


}