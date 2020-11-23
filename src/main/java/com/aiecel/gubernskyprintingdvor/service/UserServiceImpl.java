package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.Order;
import com.aiecel.gubernskyprintingdvor.model.User;
import com.aiecel.gubernskyprintingdvor.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
