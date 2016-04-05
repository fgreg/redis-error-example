package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;

/**
 * Created by greguska on 4/4/16.
 */
@Configuration
@EnableIntegration
public class IntegrationConfiguration {

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel output() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow createTileSpecs() {
        return IntegrationFlows.from(this.input())
                .<String, String>transform( message ->
                        upperCaser().toUppercase(message))
                .channel(this.output())
                .get();
    }

    @Bean
    public UpperCase upperCaser() {
        UpperCase upperCaser = new UpperCase();
        return upperCaser;
    }

}
