package com.example.short_link.linkage.service;

import com.example.short_link.linkage.dao.ShortLinkEntity;
import com.example.short_link.linkage.dto.ShortLinkMessage;
import com.example.short_link.linkage.repo.ShortLinkRepository;
import com.example.short_link.utils.ShortLinkGenerator;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
public class ShortLinkService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ShortLinkRepository repo;

    public String generateShortLink(String originalUrl) {
        String shortKey = ShortLinkGenerator.generateShortKey(originalUrl);
        // 异步发送消息到队列
        rabbitTemplate.convertAndSend("short_link.exchange", "generate.key",
                new ShortLinkMessage(originalUrl, shortKey,new Date()));
        return shortKey;
    }

    public String getOriginalUrl(String shortKey) {
        //根据shortKey查询原始URL
        Optional<ShortLinkEntity> originalUrlEntity = repo.findByShortKey(shortKey);
        if(originalUrlEntity.isPresent()) {
            return originalUrlEntity.get().getOriginalUrl();
        }else
            return null;
    }
}
