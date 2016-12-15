package com.training.sysmanager.controller;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.core.controller.BaseController;
import com.training.core.dao.BaseDao;
import com.training.core.entity.BaseEntity;
import com.training.sysmanager.entity.AclUser;

@RestController
@RequestMapping("/testCrud")
public class TestController2<T extends BaseEntity> extends BaseController<T>{

    @Resource(name = "myBatisBaseDao")
    protected BaseDao<T> baseDao;
    
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public BaseEntity getUser() throws SQLException{
        
        AclUser aclUser = (AclUser) baseDao.getEntityById((Class<T>) AclUser.class, 1);
        
        return aclUser;
    }
    
}
