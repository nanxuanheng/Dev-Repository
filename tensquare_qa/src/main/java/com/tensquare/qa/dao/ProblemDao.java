package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    //通过Query注解指定执行语句（sql,jpql）
    @Query("select p from Problem p where p.id in (select problemid from Pl where labelid = ?1) order by p.createtime desc")
    Page<Problem> findNewList(String labelId, Pageable pageable);

    @Query("select p from Problem p where p.id in (select problemid from Pl where labelid = ?1) order by p.reply desc")
    Page findHotList(String labelId, Pageable pageable);

    @Query("select p from Problem p where p.id in (select problemid from Pl where labelid = ?1) and p.reply = 0 order by p.createtime desc ")
    Page findWaitList(String labelId, Pageable pageable);
}
