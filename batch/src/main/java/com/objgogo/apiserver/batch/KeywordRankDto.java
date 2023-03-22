package com.objgogo.apiserver.batch;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KeywordRankDto {

    private String keyword;

    private Long count;
}
