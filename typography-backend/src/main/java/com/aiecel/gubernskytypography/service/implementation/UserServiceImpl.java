package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.dto.RegisterFormDTO;
import com.aiecel.gubernskytypography.dto.UserDTO;
import com.aiecel.gubernskytypography.dto.mapping.UserDTOMapper;
import com.aiecel.gubernskytypography.exception.UserAlreadyRegisteredException;
import com.aiecel.gubernskytypography.exception.UserNotFoundException;
import com.aiecel.gubernskytypography.model.Order;
import com.aiecel.gubernskytypography.model.Role;
import com.aiecel.gubernskytypography.model.SiteUser;
import com.aiecel.gubernskytypography.model.User;
import com.aiecel.gubernskytypography.repository.UserRepository;
import com.aiecel.gubernskytypography.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByUsername(String username) {
        return Mappers.getMapper(UserDTOMapper.class).toDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя не существует!")));
    }

    @Override
    public UserDTO register(RegisterFormDTO registerFormDTO) {
        if (userRepository.existsByUsername(registerFormDTO.getUsername()))
            throw new UserAlreadyRegisteredException("Такой пользователь уже существует!");

        SiteUser user = new SiteUser();
        user.setUsername(registerFormDTO.getUsername());
        user.setDisplayName(registerFormDTO.getDisplayName());
        user.setEncryptedPassword(passwordEncoder.encode(registerFormDTO.getPassword()));

        return Mappers.getMapper(UserDTOMapper.class).toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public Set<User> getUsersWithRole(Role role) {
        //fixme stub
        if (role == Role.ADMIN) {
            Set<User> admins = new HashSet<>();
            userRepository.findById(1L).ifPresent(admins::add);
            userRepository.findById(2L).ifPresent(admins::add);
            return admins;
        }
        return new HashSet<>();
    }

    @Override
    @Transactional
    public BigDecimal getDebt(long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return BigDecimal.ZERO;
        }

        BigDecimal debt = BigDecimal.ZERO;
        for (Order order : user.get().getOrders()) {
            if (!order.isPaid()) debt = debt.add(order.getPrice());
        }
        return debt;
    }
}
