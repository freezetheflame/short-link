系统架构设计
分层架构 ：

网关层 ：Spring Cloud Gateway（路由、限流、鉴权）
业务层 ：Spring Boot + JPA（短链生成、用户管理）
缓存层 ：Redis集群（热点短链缓存、访问计数）
消息队列 ：RabbitMQ集群（异步处理、流量削峰）
持久层 ：PostgreSQL（分库分表，主从复制）
安全层 ：Spring Security + JWT（用户认证与授权）



数据库设计
用户表：
CREATE TABLE users (
id BIGSERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
email VARCHAR(100),
phone VARCHAR(20),
status SMALLINT DEFAULT 1, -- 1: 启用, 0: 禁用
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
