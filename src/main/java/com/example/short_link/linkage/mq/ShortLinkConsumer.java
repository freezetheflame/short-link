package com.example.short_link.linkage.mq;

import com.example.short_link.linkage.dao.ShortLinkEntity;
import com.example.short_link.linkage.dto.ShortLinkMessage;
import com.example.short_link.linkage.repo.ShortLinkRepository;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class ShortLinkConsumer {
    @Autowired
    private ShortLinkRepository repo;

    private BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), 1000000, 0.001);

    @RabbitListener(queues = "short_link.generate.queue")
    public void handleGenerate(ShortLinkMessage message) {
        ShortLinkEntity entity = new ShortLinkEntity();
        if(isShortKeyExists(message.getShortKey())) {
            return;
        }
        entity.setShortKey(message.getShortKey());
        entity.setOriginalUrl(message.getOriginalUrl());

        repo.save(entity); // 异步写入数据库
    }
    public boolean isShortKeyExists(String shortKey) {
        if (!bloomFilter.mightContain(shortKey)) {
            return false; // 明确不存在
        }
        return repo.existsByShortKey(shortKey); // 数据库二次校验
    }
}