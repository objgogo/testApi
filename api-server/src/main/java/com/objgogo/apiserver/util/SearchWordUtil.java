package com.objgogo.apiserver.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SearchWordUtil {

    public String setKakaoSort(String sort){

        String defaultSort = "accuracy";
        if(!StringUtils.hasText(sort)){
            return defaultSort;
        } else {
            if(!sort.equals("accuracy") && !sort.equals("recency")){
                return defaultSort;
            } else {
                return sort;
            }
        }
    }

    public String setNaverSort(String sort){
        String defaultSort = "sim"; //
        if(!StringUtils.hasText(sort)){
            return defaultSort;
        } else {
            if(sort.equals("recency")){
                return "date";
            } else {
                return "sim";
            }
        }
    }
}
