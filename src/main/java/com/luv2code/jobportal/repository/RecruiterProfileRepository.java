package com.luv2code.jobportal.repository;

import com.luv2code.jobportal.entity.RecruiterProfile;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Integer> {

}
