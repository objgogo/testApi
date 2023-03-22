package com.objgogo.apiserver.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class KakaoResponseDocument {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private LocalDateTime datetime;


}
