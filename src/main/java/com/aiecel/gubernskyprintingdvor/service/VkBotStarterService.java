package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.bot.vk.VkBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VkBotStarterService {
    @Autowired
    public VkBotStarterService(VkBot bot) {
        bot.start();
    }
}
