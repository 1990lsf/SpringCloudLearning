package com.springframework.schedule;


import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.springframework.pojo.User;
import com.springframework.util.HttpUtil;
import com.springframework.wsdl.RemoteHandler;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/2/28
 * @see ScheduleTasks
 * @since 1.0
 */
@Component
public class ScheduleTasks {
    private static final Logger logger= LoggerFactory.getLogger(ScheduleTasks.class);

    @Autowired
    private Environment environment;

    @Autowired
    private RemoteHandler remoteHandler;
    @Autowired
    private RedisTemplate redisTemplate;
//    @Scheduled(cron="0 0/5 * * * *")
    public void reportNowTime(){
        LocalDateTime localDateTime= LocalDateTime.now();
        logger.info("当前时间是{}",localDateTime);
        logger.info("环境变量，授权地址{}",environment.getProperty("oauth.gateway"));
        logger.info("环境变量，授权账户{}",environment.getProperty("oauth.username"));

    }

//    @Scheduled(cron="0 0/2 * * * *")
    public void requestRibbonAddMethod(){
        logger.info("当前时间是{}",LocalDateTime.now());
        Map<String,Object> param=new ConcurrentHashMap<String,Object>();
        param.put("a",1);
        param.put("b",2);
        remoteHandler.postForObject("add",param,callResult->{
            if(callResult){
                logger.info("请求成功！");
            }
        });

    }

    @Scheduled(cron="0 0/2 * * * *")
    public void getToken(){
        logger.info("获取AccessToken");
        String url ="http://localhost:8087/uaano/oauth/token";
        Map<String,Object> params= new ConcurrentHashMap<String,Object>();
        params.put("client_id","pump");
        params.put("client_secret","pump7TUI9");
        params.put("scope","read");
        params.put("request_type","token");
        HttpResponse httpResponse=HttpUtil.post(url,new Gson().toJson(params));
        logger.info("返回结果："+httpResponse.getStatusLine());
    }

   // @Scheduled(cron="0 0/1 * * * *")
    public void insertInfo(){
        logger.info("测试redis");
        Random random = new Random();
        User user = new User();
        user.setName("name"+String.valueOf(random.nextInt(100000000)));
        user.setPawssord("pwd"+String.valueOf(random.nextInt(100000000)));
        Gson gson = new Gson();
//        BoundHashOperations<String,String,Object> boundHashOperations=redisTemplate.boundHashOps("USER");
        logger.info("===========LIST,begin=========");
        BoundListOperations<String,Object> listOperations=redisTemplate.boundListOps("USER");
        listOperations.leftPush(gson.toJson(user));
//        listOperations.leftPop();
//        listOperations.remove(1L,user);
//        listOperations.index(1);
//        listOperations.range(1,3);
//        listOperations.trim(1,3);
//        listOperations.rightPop();
//        listOperations.rightPushAll();

        logger.info("===========LIST,end=========");
        logger.info("===========HASH,begin=========");
        BoundHashOperations<String,String,Object> hashOperations=redisTemplate.boundHashOps("USERHASH");
        hashOperations.put(user.getName(),user);

//        Set<String> set= hashOperations.keys();
//        set.stream().forEach(e->{System.out.println("userHash中Key为："+e);});
        Map<String,Object> resultMap=hashOperations.entries();
        Set<Map.Entry<String, Object>> set=resultMap.entrySet();
        Iterator iterator=set.iterator();
        int i=0;
        while(iterator.hasNext()){
            Map.Entry mapEntry= (Map.Entry) iterator.next();
            Object key=mapEntry.getKey();
            Object value=mapEntry.getValue();
            logger.info("序号{},Key值:{},value值:{}",i++,key,new Gson().toJson(value));
        }

//        logger.info("即将清理的对象是：{}",user.getName());
//        hashOperations.delete(user.getName());

        logger.info("是否含有指定的键值,结果{}",hashOperations.hasKey(user.getName()));
        logger.info("redis中包含的数据数量是{}",hashOperations.size());
        logger.info("谁知道是啥{},{},{}",hashOperations.getExpire(),hashOperations.getType(),hashOperations.getKey());
        List list=hashOperations.values();
        list.forEach(e->{System.out.println(new Gson().toJson(e));});

        logger.info("===========HASH,end=========");
        logger.info("===========STRING,begin=========");
        BoundValueOperations<String,Object> valueOperations=redisTemplate.boundValueOps("USERSTRING");
        valueOperations.append(String.valueOf(random.nextInt(100000000)));
        valueOperations.set(String.valueOf(random.nextInt(100000)));
        logger.info("===========STRING,end=========");

        BoundSetOperations setOperations=redisTemplate.boundSetOps("USERSET");
        setOperations.getKey();


        BoundZSetOperations zSetOperations=redisTemplate.boundZSetOps("USERZSET");
        zSetOperations.getKey();
        zSetOperations.add(user,Double.valueOf(random.nextInt(100000000)));

        zSetOperations.size();
        zSetOperations.count(Double.valueOf(random.nextInt(1000000)),Double.valueOf(random.nextInt(200000)));
        zSetOperations.zCard();


    }

//    @Scheduled(cron="0 0/2 * * * *")
//    public void getReportData(){
//        String responseData = HttpUtil.get(environment.getProperty("order.transaction.ip"),
//                "", environment.getProperty("order.tsp.account"),
//                environment.getProperty("order.tsp.secrectKey"));
//        Gson gson = new Gson();
//        Map<String,Object> map = gson.fromJson(responseData, new TypeToken<Map<String,Object>>() {
//        }.getType());
////        byte[] bytes = map.get("data").toString().getBytes();
//
////        Object object = CompressionUtil.uncompress(bytes);
////                    String rawdata=gson.toJson(bytes);
//        String rawdata = gson.toJson(map.get("data"));
//        logger.info("返回结果{}",rawdata);
//    }
}
