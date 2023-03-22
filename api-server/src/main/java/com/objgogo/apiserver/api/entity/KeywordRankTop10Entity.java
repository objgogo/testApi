package com.objgogo.apiserver.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "keyword_rank_top_10")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordRankTop10Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = KeywordRankEntity.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_rank_id")
    private KeywordRankEntity keywordRank;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "count")
    private Long count;


}
