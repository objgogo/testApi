package com.objgogo.apiserver.api.repository;


import com.objgogo.apiserver.api.entity.SearchKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeywordEntity,Long> {
}
