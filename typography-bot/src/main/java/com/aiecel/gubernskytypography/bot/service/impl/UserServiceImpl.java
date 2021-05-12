package com.aiecel.gubernskytypography.bot.service.impl;

import com.aiecel.gubernskytypography.bot.dto.OffSiteUserDTO;
import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import com.aiecel.gubernskytypography.bot.repository.UserRepository;
import com.aiecel.gubernskytypography.bot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public static final String REGISTER_USER_ENDPOINT_URL = "http://localhost:8080/offSiteUsers/register";

    private final WebClient webClient;
    private final UserRepository userRepository;

    @Override
    public Optional<OffSiteUser> get(String username, String provider) {
        return userRepository.findByUsernameAndProvider(username, provider);
    }

    @Override
    public OffSiteUser register(OffSiteUser user) {
        //construct UserDTO
        OffSiteUserDTO userDTO = new OffSiteUserDTO(
                user.getUsername(),
                user.getDisplayName(),
                user.getProvider()
        );

        //register user
        Mono<OffSiteUserDTO> userDTOMono = webClient
                .post()
                .uri(REGISTER_USER_ENDPOINT_URL)
                .body(Mono.just(userDTO), OffSiteUserDTO.class)
                .retrieve()
                .bodyToMono(OffSiteUserDTO.class);

        userDTOMono.block();
        log.info("User registered: {}", user);
        return userRepository.save(user);
    }
}
