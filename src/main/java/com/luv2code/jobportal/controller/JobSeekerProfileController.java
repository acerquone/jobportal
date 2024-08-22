package com.luv2code.jobportal.controller;

import com.luv2code.jobportal.entity.JobSeekerProfile;
import com.luv2code.jobportal.entity.Skills;
import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.service.JobSeekerProfileService;
import com.luv2code.jobportal.service.UsersService;
import com.luv2code.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    private final JobSeekerProfileService seekerProfileService;
    private final UsersService usersService;

    @Autowired
    public JobSeekerProfileController(JobSeekerProfileService seekerProfileService, UsersService usersService) {
        this.seekerProfileService = seekerProfileService;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String jobSeekerProfile(Model model){
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        List<Skills> skillsList = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = usersService.getUserByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("Username not found."));
            Optional<JobSeekerProfile> jobSeekerProfileOptional = seekerProfileService.findById(user.getUserId());
            if (jobSeekerProfileOptional.isPresent()){

                jobSeekerProfile = jobSeekerProfileOptional.get();
                if(jobSeekerProfile.getSkills().isEmpty()){
                    skillsList.add(new Skills());
                    jobSeekerProfile.setSkills(skillsList);
                }

            }

            model.addAttribute("skills",skillsList);
            model.addAttribute("profile",jobSeekerProfile);
        }


        return "job-seeker-profile";
    }

    @PostMapping("/addNew")
    public String addNew(JobSeekerProfile jobSeekerProfile, @RequestParam("image")MultipartFile image, @RequestParam("pdf") MultipartFile pdf,Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)){

            Users user = usersService.getUserByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("Username not found."));
            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUserId());
        }

        List<Skills> skillsList = new ArrayList<>();
        model.addAttribute("profile",jobSeekerProfile);
        model.addAttribute("skills",skillsList);

        for(Skills skill: jobSeekerProfile.getSkills()){

            skill.setJobSeekerProfile(jobSeekerProfile);
        }

        String imageName="";
        String resumeName="";

        if(!image.getOriginalFilename().isEmpty()){

            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(imageName);
        }

        if(!pdf.getOriginalFilename().isEmpty()){

            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            jobSeekerProfile.setResume(resumeName);
        }

        JobSeekerProfile savedProfile = seekerProfileService.addNew(jobSeekerProfile);

        String uploadDir = "photos/candidate/"+savedProfile.getUserAccountId();

        try {
            FileUploadUtil.saveFile(uploadDir,imageName,image);
            FileUploadUtil.saveFile(uploadDir,resumeName,pdf);
        } catch (Exception exception){
            exception.printStackTrace();
        }


        return "redirect:/dashboard/";
    }

}
