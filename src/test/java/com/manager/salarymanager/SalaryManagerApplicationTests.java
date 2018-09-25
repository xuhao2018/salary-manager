package com.manager.salarymanager;

import com.manager.salarymanager.bean.Employee;
import com.manager.salarymanager.mapper.DeptmentMapper;
import com.manager.salarymanager.mapper.EmployeeMapper;
import com.manager.salarymanager.mapper.SalaryMapper;
import com.manager.salarymanager.service.DeptmentService;
import com.manager.salarymanager.service.EmployeeService;
import com.sun.org.apache.bcel.internal.classfile.Deprecated;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalaryManagerApplicationTests {

    @Autowired
    private DeptmentMapper deptmentMapper ;

    @Autowired
    private SalaryMapper salaryMapper ;

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private EmployeeService employeeService ;

    @Autowired
    private ApplicationContext applicationContext ;

    @Autowired
    private DeptmentService deptmentService ;

    @Test
    public void contextLoads() {

//        Deptment dept = deptmentMapper.selectOne(1);
//
//        System.out.println(dept);
//
//        System.out.println(salaryMapper.selectOne(1));
////
//        System.out.println(employeeMapper.selectOne(1));

//        for (Employee employee : employeeService.getAllEmps())
//            System.out.println(employee);

//         for (String name : applicationContext.getBeanDefinitionNames())

        System.out.println( deptmentService.getAll() ) ;

        System.out.println(deptmentService.getAll());






    }

}
