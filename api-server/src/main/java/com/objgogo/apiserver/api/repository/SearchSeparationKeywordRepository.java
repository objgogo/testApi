package com.objgogo.apiserver.api.repository;



import com.objgogo.apiserver.api.entity.SearchSeparationKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchSeparationKeywordRepository extends JpaRepository<SearchSeparationKeywordEntity,Long> {
}
