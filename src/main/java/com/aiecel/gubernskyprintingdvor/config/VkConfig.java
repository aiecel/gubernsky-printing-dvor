package com.aiecel.gubernskyprintingdvor.config;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkConfig {
    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(new HttpTransportClient());
    }

    @Bean
    public GroupActor groupActor() {
        return new GroupActor(199122290, "afbbfbe553dd14b90b40b7bb9515440953333508979892080445ac98bff0a2c227606193cb854f3fd4e41");
    }
}
