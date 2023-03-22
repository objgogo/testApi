package com.objgogo.apiserver.schedule;


import com.objgogo.apiserver.api.entity.KeywordRankEntity;
import com.objgogo.apiserver.api.entity.KeywordRankTop10Entity;
import com.objgogo.apiserver.api.repository.KeywordRankRepository;
import com.objgogo.apiserver.api.repository.KeywordRankTop10Repository;
import com.objgogo.apiserver.api.repository.SearchSeparationKeywordRepository;
import com.objgogo.apiserver.schedule.vo.KeywordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class KeywordRankingSchedule {

    private SearchSeparationKeywordRepository searchSeparationKeywordRepository;
    private KeywordRankRepository keywordRankRepository;
    private KeywordRankTop10Repository keywordRankTop10Repository;

    public KeywordRankingSchedule(SearchSeparationKeywordRepository searchSeparationKeywordRepository, KeywordRankRepository keywordRankRepository, KeywordRankTop10Repository keywordRankTop10Repository) {
        this.searchSeparationKeywordRepository = searchSeparationKeywordRepository;
        this.keywordRankRepository = keywordRankRepository;
        this.keywordRankTop10Repository = keywordRankTop10Repository;
    }

    @Scheduled(cron = "0 */10 * * * *" )
    public void insertKeywordRankTop10(){

        List<KeywordVo> list = searchSeparationKeywordRepository.getKeywordRank(PageRequest.of(0,10));

        if(list.size()>0){

            log.info("Keyword Rank insert Start");

            KeywordRankEntity parentEntity = keywordRankRepository.save(KeywordRankEntity.builder()
                    .createDt(LocalDateTime.now())
                    .build());

            for(KeywordVo keyword : list){
                keywordRankTop10Repository.save(KeywordRankTop10Entity.builder()
                        .keyword(keyword.getKeyword())
                        .count(keyword.getCount())
                        .keywordRank(parentEntity)
                        .build());

                log.info( "[" + keyword.getKeyword() + "] , ["+ keyword.getCount() +"]insert");
            }

        }
        log.info("Keyword Rank insert End");

    }
}
