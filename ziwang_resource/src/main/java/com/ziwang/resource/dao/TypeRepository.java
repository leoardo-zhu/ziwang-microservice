package com.ziwang.resource.dao;

import com.ziwang.resource.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Integer>, JpaSpecificationExecutor<Type> {

    @Query("select distinct new Type(t.type1Id,t.type1Name) from Type t")
    List<Type> findAllType1();

    List<Type> findByType1Id(Integer type1Id);

}
