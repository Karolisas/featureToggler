package com.example.featureToogler.service;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.repository.FeatureRepository;
import com.example.featureToogler.repository.UserFeatureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FeatureServiceTest {

    public static final long USER_ID = 1L;
    @Autowired
    FeatureService featureService;

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    UserFeatureRepository userFeatureRepository;

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
        userFeatureRepository.deleteAll();
    }

    @Test
    public void getFeatures() {
        Assertions.assertArrayEquals(List.of(feature1, feature2).toArray(), featureService.getFeatures().toArray());
        Assertions.assertEquals(List.of(feature1, feature2), featureService.getFeatures());
    }

    @Test
    public void createNewFeature_defaultDisabledTest() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
        Assertions.assertEquals(false, featureService.getFeatures().stream()
                .findFirst()
                .map(Feature::isEnabledGlobally)
                .get());
    }

    @Test
    public void editFeature_enableFeature() {
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
        featureService.enableDisableCommonFeature(feature1.getId(), true);
        Assertions.assertEquals(1, featureService.getEnabledCommonFeatures().size());
        featureService.enableDisableCommonFeature(feature1.getId(), false);
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
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
        Assertions.assertNull(featureService.getFeatures().stream()
                .filter(f -> f.getId().equals(feature.getId()))
                .findFirst()
                .orElse(null));
    }

    @Test
    public void getEnabledFeaturesTest() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

        featureService.enableDisableCommonFeature(feature1.getId(), true);
        Assertions.assertEquals(1, featureService.getEnabledCommonFeatures().size());
    }

    @Test
    public void enableUserFeature_singleFeatureTest() {
        Assertions.assertEquals(true, featureService.getFeatures().containsAll(List.of(feature1, feature2)));
        Assertions.assertEquals(0, featureService.getEnabledFeaturesOnlyForUser(feature1.getId()).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

        featureService.enableUserFeature(1L, 2L);
        Assertions.assertEquals(1, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

    }

    @Test
    public void getEnabledFeaturesOnlyForUser() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

        featureService.enableUserFeature(1L, 2L);
        Assertions.assertEquals(1, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
    }

    @Test
    public void getCommonEnabledAndUserFeatures() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
        Assertions.assertEquals(0, featureService.getCommonEnabledAndUserFeatures(USER_ID).size());


        featureService.enableUserFeature(USER_ID, feature2.getId());
        featureService.enableDisableCommonFeature(feature1.getId(), true);

        Assertions.assertEquals(1, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(1, featureService.getEnabledCommonFeatures().size());
        Assertions.assertEquals(2, featureService.getCommonEnabledAndUserFeatures(USER_ID).size());
    }

    @Test
    public void getEnabledUserFeatures_notExist() {
        Assertions.assertEquals(0, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getCommonEnabledAndUserFeatures(USER_ID).size());
    }
}