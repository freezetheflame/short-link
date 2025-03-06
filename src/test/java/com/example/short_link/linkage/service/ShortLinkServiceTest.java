package com.example.short_link.linkage.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
@org.springframework.boot.test.context.SpringBootTest
class ShortLinkServiceTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testRedisTemplate() {
        String originalUrl = "https://spring.io/projects/spring-data-redis";
        String cachedKey = redisTemplate.opsForValue().get("url_mapping:" + originalUrl);

        assertNull(cachedKey);
    }

}