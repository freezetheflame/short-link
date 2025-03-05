package com.example.short_link.linkage.controller;

import com.example.short_link.linkage.dto.AccessLog;
import com.example.short_link.linkage.service.ShortLinkService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/short_link")
public class ShortLinkController {
    @Autowired
    private ShortLinkService shortLinkService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
        String originalUrl = shortLinkService.getOriginalUrl(shortKey);
        // 发送访问日志到队列
        rabbitTemplate.convertAndSend("log.exchange", "access.key",
                new AccessLog(shortKey, new Date()));
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }

    @PostMapping("/generate")
    public String generate(String originalUrl) {
        return shortLinkService.generateShortLink(originalUrl);
    }
}
