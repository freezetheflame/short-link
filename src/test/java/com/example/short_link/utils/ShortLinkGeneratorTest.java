package com.example.short_link.utils;

import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ShortLinkGeneratorTest {

    @Test
    void generateShortKey() {
        String s = "https://chat.qwen.ai/c/5c406d1e-fb04-4a85-9333-e5b1bf10845c";
        String shortKey = ShortLinkGenerator.generateShortKey(s);
        int s2 =Hashing.murmur3_32_fixed().hashString(s, StandardCharsets.UTF_8).asInt();
        System.out.println(shortKey);
//        System.out.println(s2);

    }
}