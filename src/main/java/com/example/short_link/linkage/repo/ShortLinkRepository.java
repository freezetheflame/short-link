package com.example.short_link.linkage.repo;

import com.example.short_link.linkage.dao.ShortLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLinkEntity, Long> {
    // 根据短链接键查询记录
    Optional<ShortLinkEntity> findByShortKey(String shortKey);

    // 删除过期短链接
    void deleteByShortKey(String shortKey);

    boolean existsByShortKey(String shortKey);

    Optional<ShortLinkEntity> findByOriginalUrl(String originalUrl);
}
