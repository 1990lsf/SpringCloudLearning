package com.springframework.wsdl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/2/28
 * @see RemoteHandler
 * @since 1.0
 */
@Component
public class RemoteHandler {
    public static final Logger logger = LoggerFactory.getLogger(RemoteHandler.class);

    @Autowired
    private Environment environment;

    @Autowired
    private OAuth2RestTemplate oauth2RestTemplate;


    private static final int RETRY_COUNT=3;

    /**
     * 远程调用
     *
     * @param servcieRelativePathName 调用的服务后缀名称
     * @param requestObject           参数对象
     * @param callHandler             调用结果
     */
    public void postForObject(String servcieRelativePathName, Object requestObject, CallHandler callHandler) {
        String apiGateWay = environment.getProperty("oauth.gateway");

        int i = 1;
        try {
            for (; ; ) {

                HttpHeaders requestHeaders = new HttpHeaders();

//                requestHeaders.setContentType(new MediaType("application", "x-mggzip"));//启动gzip 支持
                requestHeaders.setContentType(new MediaType("application", "x-www-form-urlencoded"));//启动gzip 支持

                String url = apiGateWay + "ribbon/" + servcieRelativePathName;
                ResponseEntity<Map> resultEntity = oauth2RestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(requestObject, requestHeaders), Map.class);
                Map<String, Object> result = resultEntity.getBody();

                Set set=result.entrySet();
                Iterator iterator=set.iterator();
                while(iterator.hasNext()){
                    Map.Entry entry= (Map.Entry) iterator.next();
                    Object key=entry.getKey();
                    Object value=entry.getValue();
                    logger.info("返回结果："+key+","+value);
                }

//                if (result.containsKey("RS_CD") && result.get("RS_CD").equals("00")) {
//                    callHandler.call(true);
//                    return;
//                } else {
//                    logger.error("[远程服务:{},次数:{},调用错误:{}]", servcieRelativePathName, i, result);
//                    if (i == RETRY_COUNT) {
//                        return;
//                    }
//                    i++;
//                    Thread.sleep(1000);
//                }
            }
        } catch (Exception e) {
            logger.error("[远程服务:{},错误明细:{}]", servcieRelativePathName, e);
        }
    }
}
