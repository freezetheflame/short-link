package com.example.short_link.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitTemplateConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
    }

    // 如果未在配置类中定义 Bean，可直接创建
    private MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
