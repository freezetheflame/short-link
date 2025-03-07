package com.example.short_link.linkage.service;

import com.example.short_link.linkage.dao.ShortLinkEntity;
import com.example.short_link.linkage.dto.ShortLinkMessage;
import com.example.short_link.linkage.repo.ShortLinkRepository;
import com.example.short_link.utils.Base62;
import com.google.common.hash.Hashing;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ShortLinkService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ShortLinkRepository repo;

    public class res{
        String shortKey;
        Boolean isExist;

        public res(String cachedKey, boolean b) {
            this.shortKey = cachedKey;
            this.isExist = b;
        }
    }
    public res generateShortKey(String originalUrl) {
        String lockKey = "lock:generate_short_key:" + originalUrl;
        String requestId = UUID.randomUUID().toString();
        try {
            // 1. 尝试获取分布式锁（设置锁超时时间防止死锁）
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, 10, TimeUnit.SECONDS);
            if (locked == null || !locked) {
                throw new RuntimeException("获取锁失败，请稍后重试");
            }

            // 2. 检查 Redis 中是否已存在该 URL 的 short_key（避免重复生成）
            String cachedKey = redisTemplate.opsForValue().get("url_mapping:" + originalUrl);
            if (cachedKey != null) {
                return new res(cachedKey,false);
            }

            // 3. 生成新 short_key
            int hash = Hashing.murmur3_32_fixed().hashString(originalUrl, StandardCharsets.UTF_8).asInt();
            long num = hash < 0 ? ((long) hash) + Integer.MAX_VALUE + 1 : hash;
            String shortKey = Base62.encode(num);

            // 4. 检查 Redis 中是否存在该 short_key（双重验证）
            if (redisTemplate.hasKey("short_key:" + shortKey)) {
                // 冲突处理：追加递增后缀（示例逻辑）
                int retry = 0;
                do {
                    shortKey = Base62.encode(num + retry++);
                } while (redisTemplate.hasKey("short_key:" + shortKey));
            }

            // 5. 缓存结果（设置过期时间与数据库一致）
            redisTemplate.opsForValue().set("url_mapping:" + originalUrl, shortKey, 7, TimeUnit.DAYS);
            redisTemplate.opsForValue().set("short_key:" + shortKey, "1", 7, TimeUnit.DAYS);

            return new res(shortKey,true);

        } finally {
            // 6. 释放锁（使用 Lua 脚本保证原子性）
            String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            redisTemplate.execute(new DefaultRedisScript<>(luaScript, Long.class), Collections.singletonList(lockKey), requestId);
        }
    }

    public String generateShortLink(String originalUrl) {
        res shortKey_res = generateShortKey(originalUrl);
        String shortKey = shortKey_res.shortKey;
        if (!shortKey_res.isExist) {
            return shortKey;
        }
        // 异步发送消息到队列
        rabbitTemplate.convertAndSend("short_link.exchange", "generate.key",
                new ShortLinkMessage(originalUrl, shortKey,new Date()));
        return shortKey;
    }

    public String getOriginalUrl(String shortKey) {
        //根据shortKey查询原始URL
        //redis first
        String originalUrl = redisTemplate.opsForValue().get("short_link:" + shortKey);
        if (originalUrl != null) return originalUrl;


        Optional<ShortLinkEntity> originalUrlEntity = repo.findByShortKey(shortKey);
        if(originalUrlEntity.isPresent()) {
            //存入redis
            redisTemplate.opsForValue().set("short_link:" + shortKey, originalUrlEntity.get().getOriginalUrl(),7, TimeUnit.DAYS);
            return originalUrlEntity.get().getOriginalUrl();
        }else
            return null;
    }
}
