package com.example.featureToogler.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeature, Long>, JpaSpecificationExecutor<UserFeature> {

    List<UserFeature> findAllByUserId(Long userId);

}
