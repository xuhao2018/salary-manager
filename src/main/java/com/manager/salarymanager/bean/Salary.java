package com.manager.salarymanager.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties({"handler"})
public class Salary implements Serializable {

    private static final long serialVersionUID = 7150036845990905444L;
    private int id;
    private int base;
    private int welfare ;
    private int bonus ;
    private int unemploy ;
    private int legal  ;

}
