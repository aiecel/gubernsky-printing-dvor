package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.exception.UserAlreadyExistsException;
import com.aiecel.gubernskytypography.model.OffSiteUser;
import com.aiecel.gubernskytypography.repository.OffSiteUserRepository;
import com.aiecel.gubernskytypography.service.OffSiteUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OffSiteUserServiceImpl implements OffSiteUserService {
    private final OffSiteUserRepository offSiteUserRepository;

    @Override
    public boolean exists(OffSiteUser user) {
        return offSiteUserRepository.existsByUsernameAndRegistration(user.getUsername(), user.getRegistration());
    }

    @Override
    public OffSiteUser register(OffSiteUser user) {
        if (offSiteUserRepository.existsByUsernameAndRegistration(user.getUsername(), user.getRegistration())) {
            throw new UserAlreadyExistsException("Can't register user " + user.getUsername() + ": user already exists");
        }
        log.info("New user registered: {}", user);
        return offSiteUserRepository.save(user);
    }
}
