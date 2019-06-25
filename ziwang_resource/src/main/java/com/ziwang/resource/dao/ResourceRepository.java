package com.ziwang.resource.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ziwang.resource.pojo.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource,String>,JpaSpecificationExecutor<Resource>{

    // 最多下载
    List<Resource> findAllByOrderByDownloadsDesc();

    // 最新上传
    List<Resource> findAllByOrderByUploaddateDesc();

    // 热门下载
    List<Resource> findAllByIshot(Integer ishot);

    List<Resource> findAllByCategoryOrderByDownloadsDesc(Integer category);

    List<Resource> findAllByCategoryOrderByUploaddateDesc(Integer category);
}
