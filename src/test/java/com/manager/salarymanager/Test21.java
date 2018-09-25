package com.manager.salarymanager;

import com.manager.salarymanager.service.EmployeeService;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Test21 {

    @Test
 public void test1(){

        Field[] fields = EmployeeService.class.getDeclaredFields();

        for (Field field:fields){
            System.out.println(field.getType() +" "+ field.getGenericType() +" "+ field.getAnnotatedType());
        }

        Set<? extends Object> set = new HashSet<String>() ;





    }

}
