package com.objgogo.apiserver.api.controller;


import com.objgogo.apiserver.api.dto.ResponseItem;

import com.objgogo.apiserver.api.service.BlogSearchService;
import com.objgogo.apiserver.common.PagingInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("/*")
@RequestMapping("/api/blog")
@Api(tags = "[블로그] 블로그 검색 API", protocols = "http", produces = "application/json", consumes = "application/json")
public class BlogSearchController {

    private BlogSearchService blogSearchService;

    public BlogSearchController(BlogSearchService blogSearchService) {
        this.blogSearchService = blogSearchService;
    }


    @GetMapping("/search")
    public PagingInfo<ResponseItem> searchBlog(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) throws Exception {
        return blogSearchService.searchBlog(query,sort,pageNum,pageSize);
    }

    @GetMapping("/test")
    public void test(){
        System.out.println("tqtqtqtqtqtq");
    }


}
