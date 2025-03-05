package com.example.short_link.linkage.dto;

import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkMessage {
    private String originalUrl;
    private String shortKey;
    private Date createdAt;
    public ShortLinkMessage(String originalUrl) {
        this.originalUrl = originalUrl;
        this.createdAt = new Date();
    }
}
