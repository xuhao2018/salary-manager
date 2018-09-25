package com.manager.salarymanager.service;

import com.manager.salarymanager.bean.Deptment;
import com.manager.salarymanager.bean.Employee;
import com.manager.salarymanager.mapper.DeptmentMapper;
import com.manager.salarymanager.mapper.EmployeeMapper;
import com.manager.salarymanager.mapper.SalaryMapper;
import com.manager.salarymanager.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeService {


    private static final String CACHE_NAME  = "emp";

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private DeptmentService deptmentService ;

    @Autowired
    private DeptmentMapper deptmentMapper ;

    @Autowired
    private SalaryMapper salaryMapper ;

    @Autowired
    private Map<String,Set> cacheMap ;

    @Autowired
    private CacheManager  employeeCacheManager ;

    public List<Employee> getAllEmps() {

        Set<String> empsId = cacheMap.get(CACHE_NAME);

        Cache cache = employeeCacheManager.getCache(CACHE_NAME);

        List<Employee> employees ;

        // 如果 存在 缓存 就 取出 缓存中的数据
         if (empsId.size() > 0 ){
             employees = new ArrayList<>() ;
             for (String id : empsId)
                 employees.add((Employee) cache.get(id).get()) ;
         }else {
             // 如果 不存 在数据就 从数据库查询
             employees = employeeMapper.selectAll();

             // 将数据 添加进 redis
             for (Employee emp : employees)
                CacheUtil.flushCache(cache,emp.getId(),emp,empsId);
         }

        return employees ;
    }



    public Employee getOne(String id) {

        Set<String> empsId =  cacheMap.get(CACHE_NAME);

        Cache cache = employeeCacheManager.getCache("emp");

        if (empsId.contains(id))
           return  (Employee) cache.get(id).get();
        else {
            Employee employee = employeeMapper.selectOne(id);
            if (employee != null ){
               CacheUtil.flushCache(cache,employee.getId(),employee,empsId);
            }
            return employee ;
        }

    }

    @Transactional
    public void removeOne(String id) {

        Cache cache = employeeCacheManager.getCache(CACHE_NAME);

        // 从缓存中先读出数据
        Employee employee = (Employee)cache.get(id).get();

        if (employee == null)
            return  ;

        // 级联删除 数据库的数据
         employeeMapper.deleteOne(employee.getId()) ;
         salaryMapper.deleteOne(employee.getSalary().getId()) ;
        Deptment deptment = employee.getDeptment();
        deptment.setDnumber(deptment.getDnumber() -1);
        deptmentService.updateOne(deptment);

        // 删除  缓存 中的 数据
       CacheUtil.removeCache(cache,employee.getId(),cacheMap.get(CACHE_NAME));
    }

    @Transactional
    public void updateOne(Employee employee) {

        Deptment oldDept = deptmentMapper.selectOne(getOne(employee.getId()).getDid()) ;

        // 更新 数据库中 数据
         employeeMapper.updateOne(employee);
         salaryMapper.updateOne(employee.getSalary());
         //更新部门信息
        Deptment newDept = deptmentService.getOne(employee.getDid());
        newDept.setDnumber(newDept.getDnumber() + 1);
        employee.setDeptment(newDept);
        oldDept.setDnumber(oldDept.getDnumber() -1 );
         deptmentService.updateOne(newDept);
         deptmentService.updateOne(oldDept);

         //更新缓存 中 的 数据
        CacheUtil.flushCache(employeeCacheManager.getCache(CACHE_NAME),employee.getId(),employee);
    }

    @Transactional
    public void addOne(Employee employee) {

        salaryMapper.insertOne(employee.getSalary());
        employee.setSid(employee.getSalary().getId());
        Deptment deptment = deptmentService.getOne(employee.getDid());
        deptment.setDnumber(deptment.getDnumber() + 1 );
        deptmentService.updateOne(deptment);
        employee.setDeptment(deptment);
        employeeMapper.insertOne(employee) ;

         //更新 内部 缓存
      CacheUtil.flushCache(employeeCacheManager.getCache(CACHE_NAME),employee.getId(),employee,cacheMap.get(CACHE_NAME));
    }



}
