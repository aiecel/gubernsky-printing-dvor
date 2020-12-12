package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.User;

import java.math.BigDecimal;
import java.util.Set;

public interface UserService {
    Set<User> getAdmins();
    BigDecimal getDebt(long id);
}
