package com.objgogo.apiserver.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objgogo.apiserver.api.dto.KeywordRankResponse;
import com.objgogo.apiserver.api.entity.KeywordRankEntity;
import com.objgogo.apiserver.api.entity.KeywordRankTop10Entity;
import com.objgogo.apiserver.api.entity.SearchKeywordEntity;
import com.objgogo.apiserver.api.entity.SearchSeparationKeywordEntity;
import com.objgogo.apiserver.api.repository.KeywordRankRepository;
import com.objgogo.apiserver.api.repository.KeywordRankTop10Repository;
import com.objgogo.apiserver.api.repository.SearchKeywordRepository;
import com.objgogo.apiserver.api.repository.SearchSeparationKeywordRepository;
import com.objgogo.apiserver.schedule.PagingInfo;
import com.objgogo.apiserver.handler.KakaoHandler;
import com.objgogo.apiserver.handler.NaverHandler;

import com.objgogo.apiserver.schedule.vo.KeywordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.objgogo.apiserver.api.dto.ResponseItem;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BlogSearchService {

    private KakaoHandler kakaoHandler;
    private NaverHandler naverHandler;


    private SearchKeywordRepository searchKeywordRepository;
    private SearchSeparationKeywordRepository searchSeparationKeywordRepository;
    private KeywordRankRepository keywordRankRepository;
    private KeywordRankTop10Repository keywordRankTop10Repository;

    public BlogSearchService(KakaoHandler kakaoHandler, NaverHandler naverHandler, SearchKeywordRepository searchKeywordRepository, SearchSeparationKeywordRepository searchSeparationKeywordRepository, KeywordRankRepository keywordRankRepository, KeywordRankTop10Repository keywordRankTop10Repository) {
        this.kakaoHandler = kakaoHandler;
        this.naverHandler = naverHandler;
        this.searchKeywordRepository = searchKeywordRepository;
        this.searchSeparationKeywordRepository = searchSeparationKeywordRepository;
        this.keywordRankRepository = keywordRankRepository;
        this.keywordRankTop10Repository = keywordRankTop10Repository;
    }

    public PagingInfo<ResponseItem> searchBlog(String query, String sort , Integer pageNum, Integer pageSize) throws Exception {

        insertKeyword(query);

        try{
            PagingInfo<ResponseItem> res = kakaoHandler.searchBlog(query,sort,pageNum,pageSize);
            return res;
        } catch (Exception e){
            PagingInfo<ResponseItem> res = naverHandler.searchBlog(query,sort,pageNum,pageSize);
            log.info("Search Blog has Error!!");
            return res;
        }

    }

    public List<KeywordRankResponse> getKeywordTop10(){

        Optional<KeywordRankEntity> rank = keywordRankRepository.findTop1ByOrderByCreateDtDesc();
        if(rank.isPresent()){

            List<KeywordRankTop10Entity> keywordList = keywordRankTop10Repository.findByKeywordRankOrderByCountDesc(rank.get());

            List<KeywordRankResponse> response = new ArrayList<>();

            for(KeywordRankTop10Entity keyword : keywordList){
                KeywordRankResponse item = new KeywordRankResponse();
                item.setKeyword(keyword.getKeyword());
                item.setCount(keyword.getCount());

                response.add(item);
            }

            return response;
        } else {
            return null;
        }

    }

    public void insertKeyword(String query){

        SearchKeywordEntity oriKeywordEntity = searchKeywordRepository.save(
                SearchKeywordEntity.builder()
                        .keyword(query)
                        .createDt(LocalDateTime.now())
                        .build()
        );

        String[] word = query.split(" ");

        for(String s : word){
            searchSeparationKeywordRepository.save(
                    SearchSeparationKeywordEntity.builder()
                    .oriKeyword(oriKeywordEntity)
                    .separationKeyword(s)
                    .createDt(LocalDateTime.now())
                    .build()
            );
        }
    }
}
