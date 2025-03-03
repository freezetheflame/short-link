package com.example.short_link.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public DirectExchange shortLinkExchange() {
        return new DirectExchange("shortlink.exchange");
    }
}


