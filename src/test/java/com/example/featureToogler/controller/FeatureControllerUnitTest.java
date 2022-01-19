package com.example.featureToogler.controller;

import com.example.featureToogler.mockUsers.WithMockAdminUser;
import com.example.featureToogler.mockUsers.WithMockSimpleAndAdminUser;
import com.example.featureToogler.mockUsers.WithMockSimpleUser;
import com.example.featureToogler.service.FeatureService;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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
    void featureServiceExist() {
        Assertions.assertNotNull(service);
    }

    @Test
    @WithMockSimpleAndAdminUser
    void getAllFeatures_contentTypeHeaderNotSetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }

    @Test
    @WithMockSimpleAndAdminUser
    void getAllFeatures_contentTypeHeaderApplicationJsonTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithAnonymousUser
    void getAllFeatures_unAuthorizedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockSimpleUser
    void createFeature_forbiddenForSimpleUserTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(MockMvcRequestBuilders.post(FeatureController.FEATURE_API_BASE_PATH)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockAdminUser
    void createFeature_allowedForAdminTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(MockMvcRequestBuilders.post(FeatureController.FEATURE_API_BASE_PATH)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleAndAdminUser
    void getAllEnabledFeatures_allowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/enabled")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleAndAdminUser
    void getAllEnabledForUserFeatures_allowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/enabled/{userId}", 1))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockAdminUser
    void enableFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH))
                        .param("featureId", String.valueOf(1L))
                        .param("isEnabled", String.valueOf(true)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void enableFeature_userForbiddenTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH))
                        .param("id", String.valueOf(1L))
                        .param("isEnabled", String.valueOf(true)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockAdminUser
    void enableUserFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1))

                        .param("featureId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void enableUserFeature_userForbiddenTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(MockMvcRequestBuilders.put(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1))
                        .param("featureId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    @Test
    @WithMockAdminUser
    void commonAndEnabledForUserFeatures_adminAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void commonAndEnabledForUserFeatures_userAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        MockMvcRequestBuilders.get(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void deleteFeature_notAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(MockMvcRequestBuilders.delete(FeatureController.FEATURE_API_BASE_PATH + "/{userId}", 1))

                        .param("id", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    private MockHttpServletRequestBuilder addMockHttpServletRequestHeaders(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder
                .contentType(MediaType.APPLICATION_JSON);
    }
}