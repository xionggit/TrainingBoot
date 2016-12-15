package com.training.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.sun.mail.handlers.text_html;
import com.training.core.annotation.MapperClass;
import com.training.core.dao.BaseDao;
import com.training.core.dto.FlexiPageDto;
import com.training.core.entity.BaseEntity;
import com.training.sysmanager.entity.AclUser;
import com.training.sysmanager.mapper.AclUserMapper;

@Repository("myBatisBaseDao")
@SuppressWarnings("unchecked")
public class MyBatisBaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {
	
	@Resource
    @Qualifier("sessionFactory")
    private SqlSessionFactory sqlSessionFactory;
	
	private SqlSession sqlSession;
	
	@Resource
	private AclUserMapper mapper;
	
	@SuppressWarnings("rawtypes")
	public <M extends Mapper<T>> M getMapper(Class cls){
		MapperClass mapperClass = (MapperClass) cls.getAnnotation(MapperClass.class);
		if(null == mapperClass){
			throw new RuntimeException("没有注解MapperClass");
		}
		 	return (M) getSqlSession().getMapper(mapperClass.value());
	}

	@Override
	public T getEntityById(Class<T> cls, Object id){
//	    throw new RuntimeException("没有重写该方法");
	    
	    return this.getMapper(cls).selectByPrimaryKey(id);
	}

	@Override
	public void addEntity(T entity) {
		this.getMapper(entity.getClass()).insert(entity);
	}

	@Override
	public void updateEntity(T entity) {
		this.getMapper(entity.getClass()).updateByPrimaryKey(entity);
	}

	@Override
	public void deleteEntityById(Class<T> cls, Integer id) {
		this.getMapper(cls).deleteByPrimaryKey(id);
	}

	@Override
	public List<T> selectAll(Class<T> cls) {
		return this.getMapper(cls).selectAll();
	}

	@Override
	public List<T> findByLike(Example example) {
		return this.getMapper(example.getEntityClass()).selectByExample(example);
	}

	@Override
	public List<T> findByPage(Example example, FlexiPageDto flexiPageDto) {
		
		RowBounds rowBounds = new RowBounds(flexiPageDto.getOffset(), flexiPageDto.getRp());
		return this.getMapper(example.getEntityClass()).selectByExampleAndRowBounds(example, rowBounds);
	}

	@Override
	public int findRowCount(Example example) {
		return this.getMapper(example.getEntityClass()).selectCountByExample(example);
	}
	
	public SqlSession getSqlSession(){
        if (null == sqlSession){
        	synchronized (MyBatisBaseDaoImpl.class) {
        		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
			}
        }
        return this.sqlSession;
    }
	
}
