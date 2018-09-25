package com.manager.salarymanager.mapper;

import com.manager.salarymanager.bean.Deptment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptmentMapper {
    Deptment selectOne(Integer id);

    List<Deptment> selectAll() ;

    void updateOne(Deptment deptment) ;

    void insertOne(Deptment deptment);
}
