package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.model.User;

import java.math.BigDecimal;
import java.util.Set;

public interface UserService {
    Set<User> getAdmins();
    BigDecimal getDebt(long id);
}
