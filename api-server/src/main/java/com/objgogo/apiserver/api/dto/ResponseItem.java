package com.objgogo.apiserver.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ResponseItem {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private LocalDateTime datetime;
}
