package com.example.featureToogler.controller;

import com.example.featureToogler.mockUsers.WithMockAdminUser;
import com.example.featureToogler.mockUsers.WithMockSimpleAndAdminUser;
import com.example.featureToogler.mockUsers.WithMockSimpleUser;
import com.example.featureToogler.repository.UserRepository;
import com.example.featureToogler.service.FeatureService;
import com.example.featureToogler.service.UserFeatureService;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.example.featureToogler.controller.UserFeatureController.USER_FEATURE_API_BASE_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UserFeatureController.class})
@ContextConfiguration
//    @SpringBootTest
class UserFeatureControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    FeatureService service;

    @MockBean
    UserFeatureService userFeatureService;

    @MockBean
    UserRepository userRepository;

    @InjectMocks
    private UserFeatureController enableFeatureController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void featureServiceExist() {
        Assertions.assertNotNull(service);
    }

    @Test
    @WithMockSimpleAndAdminUser
    void getAllEnabledForUserFeatures_allowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        get(USER_FEATURE_API_BASE_PATH + "/{userId}/features", 1))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockAdminUser
    void enableUserFeature_adminAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(put(USER_FEATURE_API_BASE_PATH + "/{userId}/features/{featureId}", 1, 1))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void enableUserFeature_userForbiddenTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(put(USER_FEATURE_API_BASE_PATH + "/{userId}/features/{featureId}", 1, 1))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockAdminUser
    void globalAndEnabledForUserFeatures_adminAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(get(USER_FEATURE_API_BASE_PATH + "/{userId}/featuresOwn", 1))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockSimpleUser
    void globalAndEnabledForUserFeatures_userAllowedTest() throws Exception {
        mockMvc.perform(addMockHttpServletRequestHeaders(
                        get(USER_FEATURE_API_BASE_PATH + "/{userId}/features/", 1))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }


    private MockHttpServletRequestBuilder addMockHttpServletRequestHeaders(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder
                .contentType(MediaType.APPLICATION_JSON);
    }

}