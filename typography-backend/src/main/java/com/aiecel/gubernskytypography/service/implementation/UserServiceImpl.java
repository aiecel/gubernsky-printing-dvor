package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.model.Order;
import com.aiecel.gubernskytypography.model.User;
import com.aiecel.gubernskytypography.repository.UserRepository;
import com.aiecel.gubernskytypography.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Set<User> getAdmins() {
        //fixme stub
        Set<User> admins = new HashSet<>();
        userRepository.findById(1L).ifPresent(admins::add);
        userRepository.findById(2L).ifPresent(admins::add);
        return admins;
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
