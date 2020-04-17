package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;

/**
 * @program: tensquare
 * @description:
 **/
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 发表评论考虑子评论
     * @param comment
     */
    public void save(Comment comment) {
        comment.set_id(idWorker.nextId()+"");
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }
}
