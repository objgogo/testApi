package com.objgogo.apiserver.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_separation_keyword")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchSeparationKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "keyword_id")
    @ManyToOne(targetEntity = SearchKeywordEntity.class, fetch = FetchType.LAZY)
    private SearchKeywordEntity oriKeyword;

    @Column(name = "separation_keyword")
    private String separationKeyword;

    @Column(name = "create_dt")
    private LocalDateTime createDt;
}
