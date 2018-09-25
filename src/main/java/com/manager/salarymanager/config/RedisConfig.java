package com.manager.salarymanager.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.salarymanager.bean.Deptment;
import com.manager.salarymanager.bean.Employee;
import com.manager.salarymanager.bean.Salary;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableCaching
public class RedisConfig {

//     // 注册 redistemplate 实现 细粒度 缓存机制
//     @Bean
//     public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//             StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
//             template.setValueSerializer(createJsonSerializer(Object.class));
//             template.afterPropertiesSet();
//             return template;
//     }
//

    // map 用于 存放 redis 缓存中的数据 标识
     @Bean
     public Map<String,Set> cacheMap(){
         ConcurrentHashMap<String, Set> cacheMap = new ConcurrentHashMap<>();
         cacheMap.put("emp",new HashSet<String>());
         cacheMap.put("dept",new HashSet<Integer>());
         return cacheMap ;
     }

//   通用 的 反序列化 ， 可以 反序列化 任何 类型 ，但码流较大
    @Bean
    @Primary
    public RedisCacheManager defaultCacheManager(RedisConnectionFactory redisConnectionFactory){
        return createRedisCacheMager(redisConnectionFactory,Object.class) ;
    }

    // 以下的  反序列化不能 反序列化成 该类型的list ，只能 成 该类型 实例
    @Bean
    public RedisCacheManager employeeCacheManager(RedisConnectionFactory redisConnectionFactory){
        return createRedisCacheMager(redisConnectionFactory,Employee.class) ;
    }


    @Bean
    public RedisCacheManager DepetmentCacheManager(RedisConnectionFactory redisConnectionFactory){
        return createRedisCacheMager(redisConnectionFactory,Deptment.class) ;
    }

    @Bean
    public RedisCacheManager SalaryCacheManager(RedisConnectionFactory redisConnectionFactory){
        return createRedisCacheMager(redisConnectionFactory,Salary.class) ;
    }

    //创建一个cacheManager
    private  RedisCacheManager createRedisCacheMager(RedisConnectionFactory redisConnectionFactory,Class javaBeanClass){

        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

         // 序列化 器
        Jackson2JsonRedisSerializer serializer = createJsonSerializer(javaBeanClass) ;

        //设置序列化器
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(serializer));

        //创建 redisCacheManager
        return  new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);

    }


    private Jackson2JsonRedisSerializer createJsonSerializer(Class<?> beanClass){

        // 序列化 器
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(beanClass);

        //解决查询缓存list转换异常的问题
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY) ;
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL) ;
        serializer.setObjectMapper(objectMapper) ;

        return serializer ;
    }


    }


