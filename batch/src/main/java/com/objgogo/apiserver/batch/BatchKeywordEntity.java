package com.objgogo.apiserver.batch;

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
public class BatchKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword_id")
    private Long keywordId;

    @Column(name = "separation_keyword")
    private String separationKeyword;

    @Column(name = "create_dt")
    private LocalDateTime createDt;
}
