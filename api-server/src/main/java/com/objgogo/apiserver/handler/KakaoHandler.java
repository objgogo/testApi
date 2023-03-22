package com.objgogo.apiserver.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.objgogo.apiserver.common.PagingInfo;
import com.objgogo.apiserver.api.dto.KakaoResponseMeta;
import com.objgogo.apiserver.api.dto.ResponseItem;
import com.objgogo.apiserver.util.SearchWordUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class KakaoHandler {

    @Value("${kakao.key.rest-api}")
    private String restApiKey;

    private SearchWordUtil searchWordUtil;

    public KakaoHandler(SearchWordUtil searchWordUtil) {
        this.searchWordUtil = searchWordUtil;
    }

    /**
     * 블로그 검색
     * @param query 검색 쿼리(필수)
     * @param sort 정렬 조건 (accuracy / recency , default:accuracy)
     * @param pageNum 결과 페이지 번호(1~50, default:1)
     * @param pageSize 한 페이지에 보여질 문서 수( 1~50, default:10)
     * @return
     * @throws ParseException
     * @throws JsonProcessingException
     */
    public PagingInfo<ResponseItem> searchBlog(String query, String sort , Integer pageNum, Integer pageSize) throws ParseException, JsonProcessingException {

        String url = "https://dapi.kakao.com";
        WebClient kakaoWebClient = WebClient.create(url);

        String response = kakaoWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v2/search/blog")
                        .queryParam("query",query)
                        .queryParam("sort", searchWordUtil.setKakaoSort(sort))
                        .queryParam("page",pageNum)
                        .queryParam("size",pageSize).build())
                .header("Authorization","KakaoAK " + restApiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONParser parser = new JSONParser();
        JSONObject rootObj = (JSONObject)parser.parse(response);

        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        module.addDeserializer(LocalDateTime.class,dateTimeDeserializer);
        mapper.registerModule(module);

        JSONObject metaObj = (JSONObject) rootObj.get("meta");
        JSONArray documentsObj = (JSONArray) rootObj.get("documents");

        KakaoResponseMeta meta = mapper.readValue(metaObj.toJSONString(), KakaoResponseMeta.class);
        List<ResponseItem> documents = mapper.readValue(documentsObj.toJSONString(), new TypeReference<List<ResponseItem>>(){});

        PagingInfo<ResponseItem> pagingInfo = new PagingInfo(meta.getTotalCount(),documents,pageNum,pageSize);

        return pagingInfo;
    }



}
