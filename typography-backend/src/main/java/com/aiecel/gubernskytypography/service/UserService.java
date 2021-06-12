package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.dto.RegisterFormDTO;
import com.aiecel.gubernskytypography.dto.UserDTO;
import com.aiecel.gubernskytypography.model.Role;
import com.aiecel.gubernskytypography.model.User;

import java.math.BigDecimal;
import java.util.Set;

public interface UserService {
    UserDTO getUserByUsername(String username);
    UserDTO register(RegisterFormDTO registerFormDTO);
    Set<User> getUsersWithRole(Role role);
    BigDecimal getDebt(long id);
}
