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

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserFeatureServiceTest {

    public static final long USER_ID = 1L;
    @Autowired
    FeatureService featureService;

    @Autowired
    UserFeatureService userFeatureService;

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
    public void repositoriesInitialized() {
        Assertions.assertNotNull(featureRepository);
        Assertions.assertNotNull(userFeatureRepository);
    }


    @Test
    public void enableUserFeature_singleFeatureTest() {
        Assertions.assertEquals(true, featureService.getFeatures().containsAll(List.of(feature1, feature2)));
        Assertions.assertEquals(0, userFeatureService.getEnabledFeaturesOnlyForUser(feature1.getId()).size());

        userFeatureService.enableUserFeature(1L, 2L);
        Assertions.assertEquals(1, userFeatureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());
    }

    @Test
    public void getEnabledFeaturesOnlyForUser() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, userFeatureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());

        userFeatureService.enableUserFeature(1L, 2L);
        Assertions.assertEquals(1, userFeatureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());
    }

    @Test
    public void getCommonEnabledAndUserFeatures() {
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, userFeatureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledGlobalFeatures().size());
        Assertions.assertEquals(0, userFeatureService.getGlobalEnabledAndUserFeatures(USER_ID).size());


        userFeatureService.enableUserFeature(USER_ID, feature2.getId());
        featureService.setEnabledGlobalFeature(feature1.getId(), true);

        Assertions.assertEquals(1, userFeatureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(1, featureService.getEnabledGlobalFeatures().size());
        Assertions.assertEquals(2, userFeatureService.getGlobalEnabledAndUserFeatures(USER_ID).size());
    }

    @Test
    public void getEnabledUserFeatures_notExist() {
        Assertions.assertEquals(0, userFeatureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, userFeatureService.getGlobalEnabledAndUserFeatures(USER_ID).size());
    }
}