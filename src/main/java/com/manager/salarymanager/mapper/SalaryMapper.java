package com.manager.salarymanager.mapper;

import com.manager.salarymanager.bean.Salary;

public interface SalaryMapper {

    Salary selectOne(Integer id) ;

    void deleteOne(Integer id);

    void updateOne(Salary salary);

    void insertOne(Salary salary);
}
