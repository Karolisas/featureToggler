package com.example.featureToogler.service;

import com.example.featureToogler.dto.Feature;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//    @Rollback(value = false)
@Transactional
class FeatureServiceTest {

    public static final long USER_ID = 1L;
    @Autowired
    FeatureService featureService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void getFeatures() {
        Feature feature = new Feature();
        featureService.getFeatures();
        Assertions.assertEquals(Collections.emptyList(), featureService.getFeatures());
    }

    @Test
    public void createNewFeature_defaultDisabledTest() {
        featureService.createNewFeature(new Feature());
        Assertions.assertEquals(1, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
        Assertions.assertEquals(false, featureService.getFeatures().stream()
                .findFirst()
                .map(Feature::isEnabledGlobally)
                .get());
    }

    @Test
    public void editFeature_enableFeature() {
        featureService.createNewFeature(new Feature());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
        featureService.enableDisableCommonFeature(1L, true);
        Assertions.assertEquals(1, featureService.getEnabledCommonFeatures().size());
        featureService.enableDisableCommonFeature(1L, false);
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
    }

    @Test
    public void deleteFeature_Test() {
        Feature feature = new Feature();
        featureService.createNewFeature(feature);
        Assertions.assertEquals(feature, featureService.getFeatures().stream().findFirst().get());
        featureService.deleteFeature(1L);
        Assertions.assertEquals(0, featureService.getFeatures().size());
    }

    @Test
    public void getEnabledFeaturesTest() {
        featureService.createNewFeature(new Feature());
        featureService.createNewFeature(new Feature());
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

        featureService.enableDisableCommonFeature(1L, true);
        Assertions.assertEquals(1, featureService.getEnabledCommonFeatures().size());
    }

    @Test
    public void enableUserFeature_singleFeatureTest() {
        featureService.createNewFeature(new Feature());
        featureService.createNewFeature(new Feature());
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

        featureService.enableUserFeature(1L, 2L);
        Assertions.assertEquals(1, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

    }

    @Test
    public void getEnabledFeaturesOnlyForUser() {
        featureService.createNewFeature(new Feature());
        featureService.createNewFeature(new Feature());
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());

        featureService.enableUserFeature(1L, 2L);
        Assertions.assertEquals(1, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
    }

    @Test
    public void getCommonEnabledAndUserFeatures() {
        featureService.createNewFeature(new Feature());
        featureService.createNewFeature(new Feature());
        Assertions.assertEquals(2, featureService.getFeatures().size());
        Assertions.assertEquals(0, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(0, featureService.getEnabledCommonFeatures().size());
        Assertions.assertEquals(0, featureService.getCommonEnabledAndUserFeatures(USER_ID).size());


        featureService.enableUserFeature(USER_ID, 2L);
        featureService.enableDisableCommonFeature(1L, true);

        Assertions.assertEquals(1, featureService.getEnabledFeaturesOnlyForUser(USER_ID).size());
        Assertions.assertEquals(1, featureService.getEnabledCommonFeatures().size());
        Assertions.assertEquals(2, featureService.getCommonEnabledAndUserFeatures(USER_ID).size());

    }
}