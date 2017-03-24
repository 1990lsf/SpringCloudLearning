package com.springframework.remoteTest;

import com.springframework.SpringCloudClientAppApplication;
import com.springframework.wsdl.RemoteHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/2/28
 * @see RemoteHandlerTest
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringCloudClientAppApplication.class)
public class RemoteHandlerTest {
    private static final Logger logger=LoggerFactory.getLogger(RemoteHandlerTest.class);
    @Test
    public void testOauth2(){
        RemoteHandler remoteHandler= new RemoteHandler();
        Map<String,Object> params= new ConcurrentHashMap<String,Object>();
        params.put("a",1);
        params.put("b",2);
        remoteHandler.postForObject("add",params,callHandler->{if(callHandler){
            logger.info("请求成功，返回结果");
        }});
    }

}
