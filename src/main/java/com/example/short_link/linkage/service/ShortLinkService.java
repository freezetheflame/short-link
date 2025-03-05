package com.example.short_link.linkage.service;

import com.example.short_link.linkage.dto.ShortLinkMessage;
import com.example.short_link.utils.ShortLinkGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShortLinkService {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    public String generateShortLink(String originalUrl) {
        String shortKey = ShortLinkGenerator.generateShortKey(originalUrl);
        // 异步发送消息到队列
        rabbitTemplate.convertAndSend("short_link.exchange", "generate.key",
                new ShortLinkMessage(originalUrl, shortKey,new Date()));
        return shortKey;
    }

    public String getOriginalUrl(String shortKey) {
        // 查询数据库获取原始URL
        return "https://www.baidu.com";
    }
}
