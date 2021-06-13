package com.aiecel.gubernskytypography.controller;

import com.aiecel.gubernskytypography.dto.RegisterFormDTO;
import com.aiecel.gubernskytypography.dto.UserDTO;
import com.aiecel.gubernskytypography.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class SiteUserController {
    private final UserService userService;

    @GetMapping("/getByUsername")
    public UserDTO getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody @Valid RegisterFormDTO registerFormDTO) {
        return userService.register(registerFormDTO);
    }
}
