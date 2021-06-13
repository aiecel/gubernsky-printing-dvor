package com.aiecel.gubernskytypography.controller;

import com.aiecel.gubernskytypography.dto.OffSiteUserDTO;
import com.aiecel.gubernskytypography.service.OffSiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/offSiteUsers")
@RequiredArgsConstructor
public class OffSiteUserController {
    private final OffSiteUserService offSiteUserService;

    @PostMapping("/register")
    public OffSiteUserDTO register(@RequestBody @Valid OffSiteUserDTO offSiteUserDTO) {
        return offSiteUserService.register(offSiteUserDTO);
    }
}
