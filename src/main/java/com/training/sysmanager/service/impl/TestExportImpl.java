package com.training.sysmanager.service.impl;

import org.springframework.stereotype.Service;

import com.training.core.annotation.MapperClass;
import com.training.core.service.impl.MyBatisBaseServiceImpl;
import com.training.sysmanager.entity.VoteRecordMemory;
import com.training.sysmanager.mapper.VoteRecordMemoryMapper;
import com.training.sysmanager.service.TestExport;

/**
 * Created by Athos on 2016-07-02.
 */
@Service
@MapperClass(VoteRecordMemory.class)
public class TestExportImpl extends MyBatisBaseServiceImpl<VoteRecordMemory> implements TestExport {
    protected VoteRecordMemoryMapper getMapper(){
        return super.getMapper(VoteRecordMemory.class);
    }
    @Override
    public VoteRecordMemory getEntityById(Integer id) {
        
        return getMapper().selectByPrimaryKey(id);
    }
}
