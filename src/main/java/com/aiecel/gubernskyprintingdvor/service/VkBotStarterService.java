package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.bot.vk.VkBot;
import org.springframework.stereotype.Service;

@Service
public class VkBotStarterService {
    public VkBotStarterService(VkBot bot) {
        bot.start();
    }
}
