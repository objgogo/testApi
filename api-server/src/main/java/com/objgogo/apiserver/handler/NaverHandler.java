package com.objgogo.apiserver.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.objgogo.apiserver.common.PagingInfo;
import com.objgogo.apiserver.api.dto.NaverResponseMeta;
import com.objgogo.apiserver.api.dto.ResponseItem;
import com.objgogo.apiserver.util.SearchWordUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NaverHandler {

    @Value("${naver.key.client-id}")
    private String clientId;

    @Value("${naver.key.client-secret}")
    private String clientSecret;

    private SearchWordUtil searchWordUtil;

    public NaverHandler(SearchWordUtil searchWordUtil) {
        this.searchWordUtil = searchWordUtil;
    }

    public PagingInfo<ResponseItem> searchBlog(String query, String sort, Integer pageNum, Integer pageSize) throws Exception{
        String url = "https://openapi.naver.com";
        WebClient kakaoWebClient = WebClient.create(url);

        String response = kakaoWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search/blog")
                        .queryParam("query",query)
                        .queryParam("sort", searchWordUtil.setNaverSort(sort))
                        .queryParam("start",pageNum)
                        .queryParam("display",pageSize).build())
                .header("X-Naver-Client-Id",clientId)
                .header("X-Naver-Client-Secret",clientSecret)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONParser parser = new JSONParser();
        JSONObject rootObj = (JSONObject)parser.parse(response);

        ObjectMapper mapper = new ObjectMapper();

        NaverResponseMeta meta = new NaverResponseMeta();
        meta.setLastBuildDate(LocalDateTime.parse((String)rootObj.get("lastBuildDate"),DateTimeFormatter.RFC_1123_DATE_TIME));
        meta.setStart(Integer.parseInt((String)rootObj.get("start")));
        meta.setTotal(Integer.parseInt((String)rootObj.get("total")));
        meta.setDisplay(Integer.parseInt((String)rootObj.get("display")));

        JSONArray itemArrObj = (JSONArray) rootObj.get("items");

        List<ResponseItem> items = new ArrayList<>();

        for(int i=0;i< itemArrObj.size();i++){

            JSONObject itemObj = (JSONObject) itemArrObj.get(i);

            ResponseItem temp = new ResponseItem();
            temp.setBlogname((String) itemObj.get("bloggername"));
            temp.setTitle((String) itemObj.get("title"));
            temp.setContents((String) itemObj.get("description"));
            temp.setUrl((String) itemObj.get("link"));
            temp.setDatetime(LocalDateTime.parse((String) itemObj.get("postdate"),DateTimeFormatter.ofPattern("yyyyMMdd")));

            items.add(temp);
        }

        PagingInfo<ResponseItem> pagingInfo = new PagingInfo<>(meta.getTotal(),items,pageNum,pageSize);


        return pagingInfo;
    }
}
