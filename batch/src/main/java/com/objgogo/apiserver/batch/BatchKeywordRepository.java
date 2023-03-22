package com.objgogo.apiserver.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchKeywordRepository extends JpaRepository<BatchKeywordEntity, Long> {


    @Query(
            "SELECT new com.objgogo.batch.KeywordRankDto(e.separationKeyword, count(e.separationKeyword))  FROM BatchKeywordEntity e GROUP BY e.separationKeyword order by e.id desc"
    )
    List<KeywordRankDto> getKeywordRank();
}
