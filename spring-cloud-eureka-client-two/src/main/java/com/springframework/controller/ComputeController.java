package com.springframework.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Administrator on 2016/11/26.
 */
@RestController
public class ComputeController {
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String add(@RequestParam(required = false) String a, @RequestParam(required = false) String b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(a);
        logger.info(b);
        a=a==null?"1":a;
        b=b==null?"1":b;
        Integer r = Integer.valueOf(a) + Integer.valueOf(b);
        logger.info("/add,host:" + instance.getHost() + ",servie_id:" + instance.getServiceId());
        Map<String,Object> resultMap=new ConcurrentHashMap<String,Object>();
        resultMap.put("result",r);
        Gson gson = new Gson();

        return gson.toJson(resultMap);
    }

    @RequestMapping(value = "/addByGet", method = {RequestMethod.GET})
    public String addByGet(@RequestParam(required = false) String a, @RequestParam(required = false) String b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(a);
        logger.info(b);
        a=a==null?"2":a;
        b=b==null?"2":b;
        Integer r = Integer.valueOf(a) + Integer.valueOf(b);
        logger.info("/add,host:" + instance.getHost() + ",servie_id:" + instance.getServiceId());
        Map<String,Object> resultMap=new ConcurrentHashMap<String,Object>();
        resultMap.put("result",r);
        Gson gson = new Gson();

        return gson.toJson(resultMap);
    }
}
