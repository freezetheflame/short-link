package com.example.short_link.utils;


import org.apache.commons.codec.digest.MurmurHash3;

public class ShortLinkGenerator {
    private static final int HASH_SEED = 0x1234ABCD; // 固定种子保证一致性

    public static String generateShortKey(String originalUrl) {
        // 1. 计算MurmurHash3 32位哈希值
        int hash = MurmurHash3.hash32(originalUrl.getBytes(), HASH_SEED);

        // 2. 转换为Base62编码
        return Base62.encode(hash);
    }
}
