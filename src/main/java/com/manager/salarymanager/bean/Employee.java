package com.manager.salarymanager.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
// 序列化的时候忽略 handler 字段
@JsonIgnoreProperties({"handler"})
public class Employee implements Serializable {

     private static final long serialVersionUID = -8060777613061552600L;
     private String id ;
     private int did ;
     private String name ;
     private String sex ;
     private int age ;
     private int sid ;
     private Deptment deptment ;
     private Salary salary ;

}
