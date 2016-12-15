package com.training.sysmanager.service.impl;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.training.core.annotation.MapperClass;
import com.training.core.service.impl.MyBatisBaseServiceImpl;
import com.training.sysmanager.entity.AclUser;
import com.training.sysmanager.mapper.AclUserMapper;
import com.training.sysmanager.service.AclUserService;

/**
 * Created by Athos on 2016-07-02.
 */
@Service
@MapperClass(AclUser.class)
public class AclUserServiceImpl extends MyBatisBaseServiceImpl<AclUser> implements AclUserService {
    
    protected static final String EMAIL_PATTERN = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";

    protected static final String MOBILE_PATTERN = "^(1[3|4|5|7|8])[0-9]{9}$";
    
//    @CountTime
    @Override
    public AclUser findAclUserByName(String userName) {
        
        AclUser user = new AclUser();
        
        if (Pattern.matches(EMAIL_PATTERN, userName)) {
            //TODO　setEmail()
        } else if (Pattern.matches(MOBILE_PATTERN, userName)) {
            user.setUserPwd(userName);
        }else{
            user.setUserName(userName);
        }
        
        return getMapper().selectOne(user);
    }
    protected AclUserMapper getMapper(){
        return super.getMapper(AclUser.class);
    }
    @Override
    public AclUser getEntityById(Integer id) {
        
        return getMapper().selectByPrimaryKey(id);
    }
}
