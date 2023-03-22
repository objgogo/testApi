package com.objgogo.apiserver.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "keyword_rank")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordRankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @OneToMany(mappedBy = "keywordRank", targetEntity = KeywordRankTop10Entity.class, fetch = FetchType.LAZY)
    private List<KeywordRankTop10Entity> keywordRankTop10EntityList;

}
