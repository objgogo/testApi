package com.objgogo.apiserver.api.repository;

import com.objgogo.apiserver.api.entity.KeywordRankEntity;
import com.objgogo.apiserver.api.entity.KeywordRankTop10Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRankTop10Repository extends JpaRepository<KeywordRankTop10Entity,Long> {

    List<KeywordRankTop10Entity> findByKeywordRankOrderByCountDesc(KeywordRankEntity keywordRankEntity);
}
