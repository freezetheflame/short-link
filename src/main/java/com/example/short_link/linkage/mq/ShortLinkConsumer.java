package com.example.short_link.linkage.mq;

import com.example.short_link.linkage.dao.ShortLinkEntity;
import com.example.short_link.linkage.dto.ShortLinkMessage;
import com.example.short_link.linkage.repo.ShortLinkRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShortLinkConsumer {
    @Autowired
    private ShortLinkRepository repo;

    @RabbitListener(queues = "short_link.generate.queue")
    public void handleGenerate(ShortLinkMessage message) {
        ShortLinkEntity entity = new ShortLinkEntity();
        entity.setShortKey(message.getShortKey());
        entity.setOriginalUrl(message.getOriginalUrl());
        repo.save(entity); // 异步写入数据库
    }
}