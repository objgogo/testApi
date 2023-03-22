package com.objgogo.apiserver.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordRankResponse {

    private String keyword;

    private Long count;
}
