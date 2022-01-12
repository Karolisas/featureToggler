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
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.BASE_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"SIMPLE_USER", "ADMIN_USER"})
    void getAllEnabledFeatures_allowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.BASE_URL + "/common/enabled"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"SIMPLE_USER", "ADMIN_USER"})
    void getAllEnabledForUserFeatures_allowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.BASE_URL + "/1/enabled"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockSimpleUser
    void createFeature_forbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(FeatureController.BASE_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockAdminUser
    void createFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(FeatureController.BASE_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockAdminUser
    void enableFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.BASE_URL)
                        .param("id", String.valueOf(1l))
                        .param("isEnabled", String.valueOf(true)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockSimpleUser
    void enableFeature_userForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.BASE_URL)
                        .param("id", String.valueOf(1l))
                        .param("isEnabled", String.valueOf(true)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockAdminUser
    void enableUserFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.BASE_URL + "/1")
                        .param("userId", String.valueOf(1l))
                        .param("featureId", String.valueOf(1l)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockSimpleUser
    void enableUserFeature_userForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(FeatureController.BASE_URL + "/1")
                        .param("userId", String.valueOf(1l))
                        .param("featureId", String.valueOf(1l)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }


    @Test
    @WithMockAdminUser
    void commonAndEnabledForUserFeatures_adminAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.BASE_URL + "/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void commonAndEnabledForUserFeatures_userAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(FeatureController.BASE_URL + "/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void deleteFeature_notAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(FeatureController.BASE_URL + "/1")
                        .param("id", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}