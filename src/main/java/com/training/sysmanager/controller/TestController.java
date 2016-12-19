package com.training.sysmanager.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.training.core.util.XlsxUtil;
import com.training.sysmanager.entity.AclUser;
import com.training.sysmanager.entity.VoteRecordMemory;
import com.training.sysmanager.service.AclRequestTypeService;
import com.training.sysmanager.service.AclUserService;
import com.training.sysmanager.service.TestExport;

/**
 * Created by Athos on 2016-07-14.
 */
@RestController
@RequestMapping("/food")
public class TestController extends BaseController {

    @Resource
    private AclUserService aclUserService;
    
    @Resource
    private TestExport export;
    
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
    
    @RequestMapping(value = "/exportXlsx", method = RequestMethod.GET)
    public void exportXlsx() throws SQLException, IOException{
        
       List<VoteRecordMemory> records =  export.selectAll();
       String[] row = new String[5];
       
       ArrayList<String[]> infoArray = new ArrayList<String[]>();
       
       row[0] = "主键";
       row[1] = "用户id";
       row[2] = "组id";
       row[3] = "void";
       row[4] = "创建时间";
       
       infoArray.add(row);
       
       for(VoteRecordMemory record : records){
           row = new String[5];
           
           row[0] = record.getId()+"";
           row[1] = record.getUserId()+"";
           row[2] = record.getGroupId()+"";
           row[3] = record.getVoteId()+"";
           row[4] = record.getCreateTime()+"";
           
           infoArray.add(row);
       }
       
       XlsxUtil util = new XlsxUtil();
       String xls_write_Address = "C:\\Users\\Administrator\\Desktop\\export.xlsx";
       String sheetName = "sheet";
       util.write_Excel(xls_write_Address, infoArray, sheetName);
    }
}
