package com.objgogo.apiserver.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "search_keyword")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ori_keyword")
    private String keyword;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @OneToMany(mappedBy = "oriKeyword", targetEntity = SearchSeparationKeywordEntity.class,fetch = FetchType.LAZY)
    private List<SearchSeparationKeywordEntity> separationKeyword;

}
