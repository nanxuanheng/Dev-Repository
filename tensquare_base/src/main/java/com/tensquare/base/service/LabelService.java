package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.domain.Label;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: tensquare
 * @description:
 **/
@Service
public class LabelService {

    //注意涉及到增删改 开启事务管理器

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public void save(Label label) {
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public Label findOne(String labelIdx) {

        return labelDao.findById(labelIdx).get();
    }

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void delete(String labelId) {
        labelDao.deleteById(labelId);
    }

    /**
     *条件查询
     * @param map 查询条件
     * @return
     * List<T> findAll(@Nullable Specification<T> spec);
     */
    public List<Label> search(Map map) {
        Specification specification = getSpecification(map);
        return labelDao.findAll(specification);
    }

    /**
     * 思想：ORM框架操作实体相当于操作数据库中表
     * 条件分页查询  目的：通过java代码构建最终发出sql（where）语句
     * Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);
     */
    public Specification getSpecification(Map map){
        if(map.isEmpty()){
            return null;
        }
        return new Specification() {
            @Override
            //p1:根实体   //p2:query一般单表操作，可以设置sql from,where,group by ,order by4
            //p3:criteriaBuilder Predicate实例工厂
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String labelname = (String) map.get("labelname");
                if(StringUtils.isNotBlank(labelname)){
                    //sql = from label
                    //sql+= where labelname like ? (条件一)
                    //p1:查询实体中属性 p2:查询具体值
                    Predicate p1 = criteriaBuilder.like(root.get("labelname").as(String.class), "%"+labelname+"%");
                    list.add(p1);
                }
                String state = (String) map.get("state");
                if(StringUtils.isNotBlank(state)){
                    //sql+= where state = ?(条件二)
                    Predicate p2 = criteriaBuilder.equal(root.get("state").as(String.class), state);
                    list.add(p2);
                }
                String recommend = (String) map.get("recommend");
                if(StringUtils.isNotBlank(recommend)){
                    //sql+= where recommend = ?（条件三）
                    Predicate p3 = criteriaBuilder.equal(root.get("recommend").as(String.class), recommend);
                    list.add(p3);
                }
                //criteriaBuilder.and 将来条件使用and拼接，
                Predicate[] predicates = new Predicate[list.size()];
                //将list集合转为数组
                predicates = list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        };
    }

    public Page search(Map map, Pageable pageable) {
        Specification specification = getSpecification(map);
        return labelDao.findAll(specification, pageable);
    }
}
