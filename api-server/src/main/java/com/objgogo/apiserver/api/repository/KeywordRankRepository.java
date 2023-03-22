package com.objgogo.apiserver.api.repository;

import com.objgogo.apiserver.api.entity.KeywordRankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRankRepository extends JpaRepository<KeywordRankEntity,Long> {

    Optional<KeywordRankEntity> findTop1ByOrderByCreateDtDesc();
}
