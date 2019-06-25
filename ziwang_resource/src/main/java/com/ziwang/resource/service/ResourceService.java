package com.ziwang.resource.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.ziwang.resource.client.UserClient;
import com.ziwang.resource.dao.TypeRepository;
import com.ziwang.resource.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ziwang.resource.dao.ResourceRepository;
import com.ziwang.resource.pojo.Resource;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserClient userClient;


    /**
     * 根据ID查询实体
     *
     * @param id 资源Id
     * @return
     */
    public Resource findById(String id) {
        return resourceRepository.findById(id).get();
    }

    /**
     * 增加
     *
     * @param resource 资源
     */
    public void add(Resource resource) {
        resourceRepository.save(resource);
    }

    /**
     * 修改
     *
     * @param resource 资源
     */
    public void update(Resource resource) {
        resourceRepository.save(resource);
    }

    /**
     * 删除
     *
     * @param id 资源Id
     */
    public void deleteById(String id) {
        resourceRepository.deleteById(id);
    }


    public Map<String, Object> findAll() {
        Map<String, Object> result = new HashMap<>();

        List<Resource> mostDownloads = resourceRepository.findAllByOrderByDownloadsDesc();
        mostDownloads.forEach(resource -> resource.setUser(userClient.findUserById(resource.getUid()).getData()));

        List<Resource> newUploads = resourceRepository.findAllByOrderByUploaddateDesc();
        newUploads.forEach(resource -> resource.setUser(userClient.findUserById(resource.getUid()).getData()));

        result.put("most", mostDownloads);
        result.put("new", newUploads);

        return result;
    }


    /**
     * 获取所有分类
     *
     * @return [
     * {
     * type1Id:"",
     * type1Name:"",
     * type2:[
     * {
     * type2Id:"",
     * type2Name:""
     * }
     * ]
     * }
     * ]
     */
    public List<Map<String, Object>> getType() {
        List<Map<String, Object>> result = new ArrayList<>();

        List<Type> list1 = typeRepository.findAllType1();
        list1.forEach(type1 -> {
            Map<String, Object> map1 = new HashMap<>();
            Integer type1Id = type1.getType1Id();

            map1.put("type1Id", type1Id);
            map1.put("type1Name", type1.getType1Name());

            List<Map<String, Object>> list2 = new ArrayList<>();
            typeRepository.findByType1Id(type1Id).forEach(type2 -> {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("type2Id", type2.getType2Id());
                map2.put("type2Name", type2.getType2Name());

                list2.add(map2);
            });

            map1.put("type2", list2);

            result.add(map1);
        });

        return result;
    }

    /**
     * 通过分类Id返回最新资源，最多下载资源
     * @param type1Id   一级分类Id
     * @param type2Id   二级分类Id
     * @return  最新资源，最多下载资源
     */
    public Map<String, Object> getTypeById(Integer type1Id, Integer type2Id) {

        Specification<Type>  specification = new Specification<Type>() {
            @Override
            public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                if (type1Id != 0){
                    predicateList.add(cb.equal(root.get("type1Id").as(Integer.class),type1Id));
                }

                if (type2Id != 0){
                    predicateList.add(cb.equal(root.get("type2Id").as(Integer.class),type2Id));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        List<Type> types = typeRepository.findAll(specification);

        List<Resource> mosts = new ArrayList<>();
        List<Resource> news = new ArrayList<>();

        types.forEach(type -> {
            List<Resource> mostDownloads = resourceRepository.findAllByCategoryOrderByDownloadsDesc(type.getId());
            mostDownloads.forEach(resource -> resource.setUser(userClient.findUserById(resource.getUid()).getData()));
            mosts.addAll(mostDownloads);

            List<Resource> newUploads = resourceRepository.findAllByCategoryOrderByUploaddateDesc(type.getId());
            newUploads.forEach(resource -> resource.setUser(userClient.findUserById(resource.getUid()).getData()));
            news.addAll(newUploads);
        });

        Map<String, Object> result = new HashMap<>();
        result.put("new",news);
        result.put("most",mosts);

        return result;
    }

    /**
     * 查询所有热门资源
     * @return  热门资源
     */
    public List<Resource> findByHot() {
        List<Resource> hotDownloads = resourceRepository.findAllByIshot(1);
        hotDownloads.forEach(resource -> resource.setUser(userClient.findUserById(resource.getUid()).getData()));
        return hotDownloads;
    }
}
