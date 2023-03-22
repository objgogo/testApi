package com.objgogo.apiserver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BlogSearchResponse<T,T2> {

    private T meta;
    private List<T2> documents;

}
