package com.manager.salarymanager.controller;

import com.manager.salarymanager.bean.Deptment;
import com.manager.salarymanager.bean.Employee;
import com.manager.salarymanager.service.DeptmentService;
import com.manager.salarymanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    private static final String PREFIX = "employee/" ;
    @Autowired
    private EmployeeService employeeService ;

    @Autowired
    private DeptmentService deptmentService ;

    @GetMapping("/emps")
    public String getAll(Map<String,Object> map){
        List<Employee> emps = employeeService.getAllEmps();
        List<Deptment> depts = deptmentService.getAll();
        map.put("emps",emps) ;
        map.put("depts",depts) ;
        return PREFIX + "root" ;
    }


    @GetMapping("/emp/{id}")
    @ResponseBody
    public Employee get(@PathVariable("id") String id){
        return employeeService.getOne(id);
    }

    @DeleteMapping("/emp/{id}")
    public String remove(@PathVariable("id") String id){
         employeeService.removeOne(id) ;
         return "redirect:/emps" ;
    }


    @PutMapping("/emp/{id}")
    public String update(@ModelAttribute("updateEmp") Employee employee){
         employeeService.updateOne(employee) ;
         return "redirect:/emps" ;
    }

    @PostMapping("/emp")
    public String add(Employee employee){
        employeeService.addOne(employee) ;
        return "redirect:/emps" ;
    }

    @ModelAttribute
    public void getEmp(@RequestParam(value = "id",required = false) String id , Map<String,Object> map){
        if (id !=null )
            map.put("updateEmp",employeeService.getOne(id)) ;
    }


}
