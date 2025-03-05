package com.example.short_link.linkage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class ShortLinkMessage {
    private String originalUrl;
    private String shortKey;
    // 构造函数、Getter/Setter
    public ShortLinkMessage(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
