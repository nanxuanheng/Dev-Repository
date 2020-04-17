package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    //Query注解 可以写查询语句 ，也可以写增删改语句（加注解 @Modifying ）
    @Query("update Article a set a.state = '1',a.exminestate = ?2 where a.id = ?1")
    @Modifying
    void examine(String articleId, String operation);

    @Modifying
    @Query("update Article a set a.thumbup = a.thumbup+1 where a.id = ?1")
    void thumbup(String articleId);
}
