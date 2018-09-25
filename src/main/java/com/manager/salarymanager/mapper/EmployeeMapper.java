package com.manager.salarymanager.mapper;

import com.manager.salarymanager.bean.Employee;

import java.util.List;

public interface EmployeeMapper {

    List<Employee> selectAll();
    Employee selectOne(String id) ;

    void deleteOne(String id);

    void updateOne(Employee employee);

    void insertOne(Employee employee);
}
