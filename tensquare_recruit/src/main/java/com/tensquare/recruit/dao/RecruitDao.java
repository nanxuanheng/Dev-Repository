package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    //查询state为“2”四条记录 根据创建日期倒序排序
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
