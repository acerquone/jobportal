package com.luv2code.jobportal.service;

import com.luv2code.jobportal.entity.UsersTypes;
import com.luv2code.jobportal.repository.UsersTypesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypesRepository usersTypesRepository;

    public UsersTypeService(UsersTypesRepository usersTypesRepository) {
        this.usersTypesRepository = usersTypesRepository;
    }

    public List<UsersTypes> findAll() {

        return usersTypesRepository.findAll();
    }
}
