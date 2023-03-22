package com.objgogo.apiserver.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NaverResponseItem {

    private String title;

    private String link;

    private String description;

    @JsonProperty("bloggnername")
    private String bloggerName;

    @JsonProperty("bloggnerlink")
    private String bloggerLink;

    private LocalDate postdate;
}
