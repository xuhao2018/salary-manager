package com.manager.salarymanager.controller;

import com.manager.salarymanager.bean.Deptment;
import com.manager.salarymanager.service.DeptmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class DeptmenrController {

    private static final String PREFIX =  "deptment/" ;

    @Autowired
    private DeptmentService deptmentService ;

     @GetMapping("/depts")
  public  String getAll(Map<String,Object> map){
         List<Deptment> depts = deptmentService.getAll();
         map.put("depts",depts) ;
         return PREFIX + "root" ;
     }


     @PutMapping("/dept/{id}")
      public String update(@ModelAttribute("updateDept") Deptment deptment){
          deptmentService.updateOne(deptment);
          return "redirect:/depts" ;
      }

      @PostMapping("/dept")
    public String  add(Deptment deptment){
          deptmentService.addOne(deptment);
          return "redirect:/depts" ;
      }

      @DeleteMapping("/dept/{id}")
    public String  remove(@PathVariable("id") Integer id){
          deptmentService.removeOne(id) ;
          return "redirect:/depts" ;
    }

    @ModelAttribute
    public void getUpdateDept(@RequestParam(value = "did",required = false) Integer id,Map<String,Object> map ){
         if (id !=null)
        map.put("updateDept",deptmentService.getOne(id)) ;
    }




}
