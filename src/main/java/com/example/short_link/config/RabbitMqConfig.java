package com.example.short_link.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    // 声明交换器（例如：短链接事件交换器）
    @Bean
    public DirectExchange shortLinkExchange() {
        return new DirectExchange("short_link.exchange");
    }

    // 声明队列（例如：短链接生成队列）
    @Bean
    public Queue shortLinkGenerateQueue() {
        return new Queue("short_link.generate.queue");
    }

    // 绑定队列到交换器
    @Bean
    public Binding bindingGenerateQueue(DirectExchange shortLinkExchange, Queue shortLinkGenerateQueue) {
        return BindingBuilder.bind(shortLinkGenerateQueue).to(shortLinkExchange).with("generate.key");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    // 在配置类中声明死信队列
//    @Bean
//    public Queue generateQueue() {
//        return QueueBuilder.durable("short_link.generate.queue")
//                .deadLetterExchange("dlq.exchange")
//                .build();
//    }
}


