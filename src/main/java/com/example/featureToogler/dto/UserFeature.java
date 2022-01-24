package com.example.featureToogler.dto;


import javax.persistence.*;

@Entity
@Table(name = "USER_FEATURES")
public class UserFeature {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "feature_id")
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
