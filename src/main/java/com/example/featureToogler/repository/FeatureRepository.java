package com.example.featureToogler.repository;

import com.example.featureToogler.dto.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long>, JpaSpecificationExecutor<Feature> {

    List<Feature> findAll();

    @Query("select f from Feature f where f.enabledGlobally = true")
    List<Feature> findEnabledFeatures();
}
