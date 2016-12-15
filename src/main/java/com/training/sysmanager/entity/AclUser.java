package com.training.sysmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.core.annotation.MapperClass;
import com.training.core.entity.BaseEntity;
import com.training.sysmanager.mapper.AclUserMapper;

/**
 * Created by Athos on 2016-06-29.
 */
@NameStyle(value = Style.camelhumpAndLowercase)
@Table(name="tbl_sysmgr_acluser")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@MapperClass(AclUserMapper.class)
public class AclUser extends BaseEntity implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 387659050151643679L;
    /**
     * 用户名
     */
    @Column
    private String userName;
    /**
     * 密码
     */
    @Column
    private String userPwd;
    /**
     * 角色 json 格式  或逗号间隔 或转换为数组
     */
    @Column
    private String roleIds;

    @Transient
    private String roleNames;

    /**
     * 转换为数据 瞬时
     */
    @Transient
    private String [] rolesesArray;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String[] getRolesesArray() {
        return rolesesArray;
    }

    public void setRolesesArray(String[] rolesesArray) {
        this.rolesesArray = rolesesArray;
    }
}
