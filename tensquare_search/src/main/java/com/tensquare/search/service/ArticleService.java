package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * @program: tensquare_parent
 * @description:
 **/
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Article article) {
        article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    /**
     *
     * @param keywords 查询标题 ，内容
     * @param pageable
     * @return
     */
    public Page<Article> search(String keywords, Pageable pageable) {
        return articleDao.findByTitleOrContentLike(keywords, keywords, pageable);
    }
}
