package com.objgogo.apiserver.api.repository;



import com.objgogo.apiserver.api.entity.SearchSeparationKeywordEntity;
import com.objgogo.apiserver.schedule.vo.KeywordVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchSeparationKeywordRepository extends JpaRepository<SearchSeparationKeywordEntity,Long> {

    @Query(
            "SELECT new com.objgogo.apiserver.schedule.vo.KeywordVo(e.separationKeyword, count(e.separationKeyword))  " +
                    "FROM SearchSeparationKeywordEntity e " +
                    "GROUP BY e.separationKeyword " +
                    "ORDER BY count(e.separationKeyword) DESC "

    )
    List<KeywordVo> getKeywordRank(Pageable pageable);


}
