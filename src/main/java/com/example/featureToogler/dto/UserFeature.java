package com.example.featureToogler.dto;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_FEATURES")
public class UserFeature {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long featureId;


    public Long getUserId() {
        return userId;
    }

    public UserFeature setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public UserFeature setFeatureId(Long featureId) {
        this.featureId = featureId;
        return this;
    }
}
