package com.manager.salarymanager.util;

import org.springframework.cache.Cache;

import java.util.Set;

public class CacheUtil {


     public static void removeCache(Cache cache, String key,  Set<?> set){

          //  先更新 redis 中 缓存
          cache.evict(key);

          //更新服务器内部缓存
         if (set !=null && set.contains(key))
             set.remove(key) ;
     }

     // 更新缓存
    public static void flushCache(Cache cache,Object key,Object value,Set set){
          cache.put(key,value);
          if (set !=null)
              set.add(key) ;
    }


    public static void flushCache(Cache cache,Object key,Object value){
        flushCache(cache,key,value,null);
    }




}
