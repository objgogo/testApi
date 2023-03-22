package com.objgogo.apiserver.api.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NaverResponseMeta {

    private LocalDateTime lastBuildDate;

    private Integer total;

    private Integer start;

    private Integer display;


}
