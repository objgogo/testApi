package com.objgogo.apiserver.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BatchApplicationTests {

    @Autowired
    BatchKeywordRepository batchKeywordRepository;


    @Test
    void contextLoads() {
        List<KeywordRankDto> list = batchKeywordRepository.getKeywordRank();

        for(KeywordRankDto d : list){
            System.out.println(d.toString());
        }


    }

}
