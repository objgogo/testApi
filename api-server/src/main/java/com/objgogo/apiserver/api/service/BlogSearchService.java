package com.objgogo.apiserver.api.service;

import com.objgogo.apiserver.api.entity.SearchKeywordEntity;
import com.objgogo.apiserver.api.entity.SearchSeparationKeywordEntity;
import com.objgogo.apiserver.api.repository.SearchKeywordRepository;
import com.objgogo.apiserver.api.repository.SearchSeparationKeywordRepository;
import com.objgogo.apiserver.common.PagingInfo;
import com.objgogo.apiserver.handler.KakaoHandler;
import com.objgogo.apiserver.handler.NaverHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.objgogo.apiserver.api.dto.ResponseItem;


import java.time.LocalDateTime;

@Slf4j
@Service
public class BlogSearchService {

    private KakaoHandler kakaoHandler;
    private NaverHandler naverHandler;


    private SearchKeywordRepository searchKeywordRepository;
    private SearchSeparationKeywordRepository searchSeparationKeywordRepository;

    public BlogSearchService(KakaoHandler kakaoHandler, NaverHandler naverHandler, SearchKeywordRepository searchKeywordRepository, SearchSeparationKeywordRepository searchSeparationKeywordRepository) {
        this.kakaoHandler = kakaoHandler;
        this.naverHandler = naverHandler;
        this.searchKeywordRepository = searchKeywordRepository;
        this.searchSeparationKeywordRepository = searchSeparationKeywordRepository;
    }

    public PagingInfo<ResponseItem> searchBlog(String query, String sort , Integer pageNum, Integer pageSize) throws Exception {

        insertKeyword(query);

        try{
            PagingInfo<ResponseItem> res = kakaoHandler.searchBlog(query,sort,pageNum,pageSize);
            return res;
        } catch (Exception e){
            PagingInfo<ResponseItem> res = naverHandler.searchBlog(query,sort,pageNum,pageSize);
            log.debug("Search Blog has Error!!");
            return res;
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
