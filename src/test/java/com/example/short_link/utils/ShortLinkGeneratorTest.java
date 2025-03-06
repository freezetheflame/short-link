package com.example.short_link.utils;

import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import com.example.short_link.linkage.service.ShortLinkService;

class ShortLinkGeneratorTest {

    @Test
    void generateShortKeytest() {
        String s = "https://chat.qwen.ai/c/5c406d1e-fb04-4a85-9333-e5b1bf10845c";
        ShortLinkService shortLinkService = new ShortLinkService();
        String shortKey = shortLinkService.generateShortKey(s);
        int s2 =Hashing.murmur3_32_fixed().hashString(s, StandardCharsets.UTF_8).asInt();
        System.out.println(shortKey);
//        System.out.println(s2);

    }
}