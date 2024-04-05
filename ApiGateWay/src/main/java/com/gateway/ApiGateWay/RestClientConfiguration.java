package com.gateway.ApiGateWay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient.Builder restClientBuilder() {
        // Define and configure the RestClient.Builder bean here
        return RestClient.builder();
    }
}
