package com.luv2code.jobportal.service;

import com.luv2code.jobportal.entity.*;
import com.luv2code.jobportal.repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){

        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter){

        List<IRecruiterJobs> recruiterJobsDto = jobPostActivityRepository.getRecruiterJobs(recruiter);

        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

        for (IRecruiterJobs job: recruiterJobsDto){

            JobLocation location = new JobLocation(job.getLocationId(),job.getCity(),job.getState(),job.getCountry());
            JobCompany company = new JobCompany(job.getCompanyId(),job.getName(),"");
            RecruiterJobsDto jobsDto = new RecruiterJobsDto(job.getTotalCandidates(),job.getJob_post_id(),job.getJob_Title(),location,company);
            recruiterJobsDtoList.add(jobsDto);
        }

        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(int id) {

        return jobPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));

    }
}
