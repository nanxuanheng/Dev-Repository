package com.tensquare.spit.dao;

import com.tensquare.spit.domain.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

//实体类型指定将来集合名称  实体类小写
public interface SpitDao extends MongoRepository<Spit, String> {

    //根据上级吐槽id查询吐槽数据
    public Page<Spit> findByParentid(String parentid, Pageable pageable);

}
