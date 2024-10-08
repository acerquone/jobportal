package com.luv2code.jobportal.service;

import com.luv2code.jobportal.entity.JobSeekerProfile;
import com.luv2code.jobportal.repository.JobSeekerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }

    public Optional<JobSeekerProfile> findById(Integer id) {

        return jobSeekerProfileRepository.findById(id);
    }

    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {

        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }
}
