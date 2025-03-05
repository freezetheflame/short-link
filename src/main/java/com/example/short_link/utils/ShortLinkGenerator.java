package com.example.short_link.utils;


import io.netty.util.HashingStrategy;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class ShortLinkGenerator {
    private static final int HASH_SEED = 0x1234ABCD; // 固定种子保证一致性

    public static String generateShortKey(String originalUrl) {
        // 1. 计算MurmurHash3 32位哈希值
        int hash = Hashing.murmur3_32_fixed().hashString(originalUrl, StandardCharsets.UTF_8).asInt();
        // 2. 转换为Base62编码
        long num = hash<0?((long)hash) + Integer.MAX_VALUE + 1:hash;
//        System.out.println(num);
        return Base62.encode(num);
    }
}
