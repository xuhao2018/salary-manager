package com.manager.salarymanager.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
//json序列化 忽略 handler属性
@JsonIgnoreProperties({"handler"})
public class Deptment implements Serializable {

    private static final long serialVersionUID = 2584043152484649204L;
    private int did ;
    private String dname ;
    private int dnumber ;


}
