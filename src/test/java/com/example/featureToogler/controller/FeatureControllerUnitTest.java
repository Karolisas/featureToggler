package com.example.featureToogler.controller;

import com.example.featureToogler.mockUsers.WithMockAdminUser;
import com.example.featureToogler.mockUsers.WithMockSimpleUser;
import com.example.featureToogler.service.FeatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(FeatureController.class)
@ContextConfiguration
//    @SpringBootTest
class FeatureControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    FeatureService service;

    @InjectMocks
    private FeatureController featureController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser(roles = {"SIMPLE_USER", "ADMIN_USER"})
    void getAllFeatures_allowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"SIMPLE_USER", "ADMIN_USER"})
    void getAllEnabledFeatures_allowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/common/enabled")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = {"SIMPLE_USER", "ADMIN_USER"})
    void getAllEnabledForUserFeatures_allowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/{userId}/enabled", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void createFeature_forbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(FeatureController.FEATURE_API_BASE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockAdminUser
    void createFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(FeatureController.FEATURE_API_BASE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockAdminUser
    void enableFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH)
                        .param("id", String.valueOf(1L))
                        .param("isEnabled", String.valueOf(true)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void enableFeature_userForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH)
                        .param("id", String.valueOf(1L))
                        .param("isEnabled", String.valueOf(true)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockAdminUser
    void enableUserFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1)
                        .param("featureId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void enableUserFeature_userForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1)
                        .param("featureId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    @Test
    @WithMockAdminUser
    void commonAndEnabledForUserFeatures_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void commonAndEnabledForUserFeatures_userAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void deleteFeature_notAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1)
                        .param("id", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}