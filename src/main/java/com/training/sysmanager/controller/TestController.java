package com.training.sysmanager.controller;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.training.core.controller.BaseController;
import com.training.core.entity.BaseEntity;
import com.training.core.mybatis.DataSourceContextHolder;
import com.training.sysmanager.entity.AclUser;
import com.training.sysmanager.service.AclRequestTypeService;
import com.training.sysmanager.service.AclUserService;

/**
 * Created by Athos on 2016-07-14.
 */
@RestController
@RequestMapping("/food")
public class TestController extends BaseController {

    @Resource
    private AclUserService aclUserService;
    
    @Resource
    private AclRequestTypeService aclRequestTypeService;

    @PreAuthorize("hasAuthority('AUTH_FOOD_QUERY')")
    @RequestMapping(value = "/queryFood",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void foods(){
        System.out.println(aclRequestTypeService.getEntityById(1));
        System.out.println("读取食物.....");
    }

    @PreAuthorize("hasAuthority('AUTH_FOOD_ADD')")
    @RequestMapping(value = "/addFood", method = RequestMethod.GET)
    public  void add(){
        System.out.println("新增食物");
    }

    @RequestMapping(value = "/selectCountByNeId", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('AUTH_FOOD_DELETE')")
    public  void selectCountByNeId() throws SQLException {
        DataSourceContextHolder.read();
        System.out.println(aclRequestTypeService.getEntityById(1).getPronoun());
        DataSourceContextHolder.write();
        System.out.println(aclRequestTypeService.getEntityById(1).getPronoun());
    }
    
    
    
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Object getUser() throws SQLException{
        
        AclUser aclUser = aclUserService.getEntityById(1);//selectAll();
        
//        AclUser aclUser = new AclUser();
//        aclUser.setId(1);
//        aclUser.setCreateTime(new Date());
//        aclUser.setRoleIds("1,2");
//        
//        BaseEntity user = aclUserService.getEntityById(1);
        return aclUser;
    }
    
    @RequestMapping(value = "/getUser2", method = RequestMethod.GET)
    public Object getUser2() throws SQLException{
        
        Object aclUser = aclUserService.selectAll();
        return aclUser;
    }
    
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public void addUser() throws SQLException{
        
        AclUser aclUser = new AclUser();
        aclUser.setId(2);
        aclUser.setCreateTime(new Date());
        aclUser.setRoleIds("1,2");
        aclUser.setUserName("test1");
        aclUser.setUserPwd("123456  ");
        
        aclUserService.addEntity(aclUser);
    }
    
    @RequestMapping(value = "/delUser", method = RequestMethod.GET)
    public void delUser() throws SQLException{
        
        aclUserService.deleteEntityById(2);
    }
    
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public void updateUser() throws SQLException{
        
        AclUser aclUser = new AclUser();
        aclUser.setId(2);
        aclUser.setCreateTime(new Date());
        aclUser.setRoleIds("1,2");
        aclUser.setUserName("test1");
        aclUser.setUserPwd("123szxc");
        
        aclUserService.updateEntity(aclUser);
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView getHello1() throws SQLException{
        
        return new ModelAndView("hello");
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/getHello2", method = RequestMethod.GET)
    public ModelAndView getHello2() throws SQLException{
        
        return new ModelAndView("test/home");
    }
}
