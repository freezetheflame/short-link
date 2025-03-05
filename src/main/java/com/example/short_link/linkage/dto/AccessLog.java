package com.example.short_link.linkage.dto;

import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLog {
    private String shortKey; // 短链接键
    private Date accessTime; // 访问时间



}
