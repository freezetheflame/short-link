package com.example.short_link.linkage.repo;

import com.example.short_link.linkage.dao.ShortLinkEntity;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;
@org.springframework.boot.test.context.SpringBootTest
class ShortLinkRepositoryTest {

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    Logger logger = org.apache.logging.log4j.LogManager.getLogger(ShortLinkRepositoryTest.class);
    @Test
    public void testFind(){
        //test findByOriginalUrl
        Optional<ShortLinkEntity> shortLinkEntity = shortLinkRepository.findByOriginalUrl("https://www.baidu.com");
        if(shortLinkEntity.isPresent()){
            System.out.println(shortLinkEntity.get().getShortKey());
            logger.info("shortKey:{}",shortLinkEntity.get().getShortKey());
        }else {
            System.out.println("not found");
            logger.info("not found");
        }
        //test findByShortKey
    }

    @Test
    void testexistsByShortKey(){
        boolean exists = shortLinkRepository.existsByShortKey("29dN8W");
        System.out.println(exists);
    }
}