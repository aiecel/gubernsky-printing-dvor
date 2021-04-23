package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.model.Role;
import com.aiecel.gubernskytypography.model.User;

import java.math.BigDecimal;
import java.util.Set;

public interface UserService {
    Set<User> getUsersWithRole(Role role);
    BigDecimal getDebt(long id);
}
