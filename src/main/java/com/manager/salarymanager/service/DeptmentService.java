package com.manager.salarymanager.service;

import com.manager.salarymanager.bean.Deptment;
import com.manager.salarymanager.bean.Employee;
import com.manager.salarymanager.mapper.DeptmentMapper;
import com.manager.salarymanager.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DeptmentService {

    private static final String CACHE_NAME  = "dept" ;

 @Autowired
    private DeptmentMapper deptmentMapper ;

 @Autowired
   private CacheManager DeptmentCacheManager ;

 @Autowired
   private CacheManager employeeCacheManager ;

  @Autowired
  private Map<String,Set> cacheMap ;

  public List<Deptment> getAll(){
      Set<Integer> deptsId = cacheMap.get(CACHE_NAME);
      Cache cache = DeptmentCacheManager.getCache(CACHE_NAME);
       List<Deptment> depts ;
      if (deptsId.size() > 0){
          depts = new ArrayList<>() ;
          for (Integer deptId : deptsId){
              depts.add((Deptment) cache.get(deptId).get()) ;
          }
      } else {
           depts = deptmentMapper.selectAll();
           for (Deptment dept : depts)
               CacheUtil.flushCache(cache,dept.getDid(),dept,deptsId);
      }
      return depts ;
  }

  public Deptment getOne(Integer id){
      Deptment deptment ;
      Cache cache = DeptmentCacheManager.getCache(CACHE_NAME);
      Set set = cacheMap.get(CACHE_NAME);
      //如果 缓存中有就从缓存中 取
       if (set.contains(id)){
            deptment = (Deptment) cache.get(id).get();
       } else {
           deptment = deptmentMapper.selectOne(id);
           if (deptment!=null)
           CacheUtil.flushCache(cache,id,deptment,set);
       }
       return deptment ;
  }


  public void updateOne(Deptment deptment){
      deptmentMapper.updateOne(deptment) ;
       CacheUtil.flushCache(DeptmentCacheManager.getCache(CACHE_NAME),deptment.getDid(),deptment);

//       // 需要 更新redis 中 所有emp 的 dept的number
      Set<String> empsId = cacheMap.get("emp");

      Cache cache = employeeCacheManager.getCache("emp");

      for (String empId : empsId){
          Employee employee = (Employee)cache.get(empId).get();
          if (deptment.getDid() == employee.getDid()){
              employee.setDeptment(deptment);
              cache.put(empId,employee);
          }
      }
  }


    public void addOne(Deptment deptment) {
       deptmentMapper.insertOne(deptment) ;
       CacheUtil.flushCache(DeptmentCacheManager.getCache(CACHE_NAME),deptment.getDid(),deptment,cacheMap.get(CACHE_NAME));
    }


    public void removeOne(Integer id) {

    }



}
