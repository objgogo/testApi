package com.objgogo.apiserver.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.objgogo.apiserver.api.dto.BlogSearchResponse;
import com.objgogo.apiserver.api.dto.KakaoResponseDocument;
import com.objgogo.apiserver.api.dto.KakaoResponseMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
class BlogSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${kakao.key.rest-api}")
    private String restApiKey;

    @Test
    void searchBlogApiTest() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/blog/search")
                .queryParam("keyword","집짓기");
        for(int i=0;i<200;i++){
            mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
        }
    }

    @Test
    void searchBlogApiCallTest(){

        String query = "https://brunch.co.kr/@tourism 집짓기";
        String sort ="accuracy";
        Integer pageNum =1;
        Integer pageSize= 10;

        String url = "https://dapi.kakao.com";
        WebClient kakaoWebClient = WebClient.create(url);

        String res = kakaoWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v2/search/blog")
                        .queryParam("query",query)
                        .queryParam("sort",sort)
                        .queryParam("page",pageNum)
                        .queryParam("size",pageSize)
                        .build())
                .header("Authorization","KakaoAK " + restApiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("ddd");
    }

    @Test
    void searchBlogApiCallResponseTest() throws JsonProcessingException, ParseException {

        String query = "집짓기";
        String sort ="accuracy";
        Integer pageNum =1;
        Integer pageSize= 10;

        String url = "https://dapi.kakao.com";
        WebClient kakaoWebClient = WebClient.create(url);

        String responseStr = kakaoWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v2/search/blog")
                        .queryParam("query",query)
                        .queryParam("sort",sort)
                        .queryParam("page",pageNum)
                        .queryParam("size",pageSize)
                        .build())
                .header("Authorization","KakaoAK " + restApiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONParser parser = new JSONParser();
        JSONObject rootObj = (JSONObject)parser.parse(responseStr);
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        module.addDeserializer(LocalDateTime.class,dateTimeDeserializer);
        mapper.registerModule(module);

        JSONObject metaObj = (JSONObject) rootObj.get("meta");
        JSONArray documentsObj = (JSONArray) rootObj.get("documents");

        BlogSearchResponse res = new BlogSearchResponse(
                mapper.readValue(metaObj.toJSONString(), KakaoResponseMeta.class),
                mapper.readValue(documentsObj.toJSONString(), new TypeReference<List<KakaoResponseDocument>>(){})
        );

        System.out.println(res.toString());
    }

}