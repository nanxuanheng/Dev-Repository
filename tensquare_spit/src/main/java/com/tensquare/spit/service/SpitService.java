package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.domain.Spit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @program: tensquare
 * @description:
 **/
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 发布吐槽 1、保存吐槽数据  2、评论吐槽数据
     * @param spit
     */
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setShare(0); //分享数
        spit.setState("1"); //状态
        spit.setThumbup(0); //点赞数
        spit.setVisits(0); //浏览数
        spit.setComment(0);//评论数
        //如果spit对象中parentid属性有值--在进行评论
        if(StringUtils.isNotBlank(spit.getParentid())){
            //上级吐槽数据 评论数+1，浏览数+1
            Spit parentSpit = spitDao.findById(spit.getParentid()).get();
            parentSpit.setVisits(parentSpit.getVisits()+1); // 评论数+1
            parentSpit.setComment(parentSpit.getComment()+1); //浏览数+1
            spitDao.save(parentSpit);
        }
        spitDao.save(spit);
    }

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String spitId) {
        return spitDao.findById(spitId).get();
    }

    public Page<Spit> findByParentId(String parentid, Pageable pageable) {
        return spitDao.findByParentid(parentid, pageable);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 对数据库中文档数据 thumbupt+1
     * @param spitId
     */
    public void updateThumbup(String spitId) {
        //方式一
        Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup(spit.getThumbup()+1);
        spitDao.save(spit);
        //方式二：
       /* Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");*/
    }

    /**
     * 对数据库中的数据 visits+1
     * @param spitId
     */
    public void updateVisits(String spitId) {
        Spit spit = spitDao.findById(spitId).get();
        spit.setVisits(spit.getVisits()+1);
        spitDao.save(spit);


    }
    /**
     * 对数据库的数据share+1
     * @param spitId
     */
    public void updateShare(String spitId) {

        /*Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("visits",1);
        mongoTemplate.updateFirst(query,update,"spit");*/

        Spit spit = spitDao.findById(spitId).get();
        spit.setShare(spit.getShare()+1);
        spitDao.save(spit);

    }
}
