package com.springframework.util;


import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/2
 * @see HttpUtil
 * @since 1.0
 */
@Component
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    public static HttpResponse post(String url ,String message){
        RequestConfig requestConfig=RequestConfig.custom().setSocketTimeout(60 * 1000)
                .setConnectTimeout(60 * 1000).build();

        CloseableHttpAsyncClient httpAsyncClientlient =  HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
        HttpResponse httpResponse = null;//得到请求
        try {
        httpAsyncClientlient.start();
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("Content-Type", "application/json");
        postRequest.addHeader("Accept", "application/json");

        StringEntity stringEntity = null;//封装参数，将原有参数对象转化成string 传入
            stringEntity = new StringEntity(message);

        postRequest.setEntity(stringEntity);


        Future<HttpResponse> response = httpAsyncClientlient.execute(postRequest,null);
        httpResponse = response.get();

        if (logger.isDebugEnabled()) {
            logger.debug("Response: " + httpResponse.getStatusLine());
            logger.debug("Shutting down");
        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
         catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
     finally {
        if (null != httpAsyncClientlient) {
            try {
                httpAsyncClientlient.close();
            } catch (IOException e) {
                logger.error("httpclient关闭异常", e);
            }
        }
    }
        return httpResponse;

    }
    public static String get(String url, String param, String account, String secrectKey) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60 * 1000).setConnectTimeout(60 * 1000)
                .build();
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
        String result = null;
        try {

            String md5 = getParamMd5(url, account);
            String contentMd5 = "Content-Md5: " + md5;
            Calendar cd = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateStr = sdf.format(cd.getTime());
            String dataStr = "X-Date: " + dateStr;
            String macData = dataStr + "\n" + contentMd5;
            String Authorization = null;

            Authorization = HmacEncodeStr.hmacEncodeStr(secrectKey, macData, account);

            url = url + "?" + param;
            httpClient.start();
            HttpGet getRequest = new HttpGet(url);
            getRequest.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            getRequest.addHeader(HttpHeaders.CONTENT_MD5, md5);
            getRequest.addHeader("X-Date", dateStr);
            getRequest.addHeader(HttpHeaders.AUTHORIZATION, Authorization);
            getRequest.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip,deflate");

            Future<HttpResponse> response = httpClient.execute(getRequest, null);//执行请求

            HttpResponse httpResponse = response.get();
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            if (responseCode == HttpStatus.SC_OK) {//状态码=202
                //返回结果
                result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                logger.info(String.format("请求{%s},请求成功。返回结果{%s}", url, result));
            } else {
                logger.error(String.format("请求{%s},请求失败。返回状态码", url, responseCode));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("httpclient关闭异常", e);

                }
            }
        }
        return result;
    }

    private static final String getParamMd5(String url, String account) {
        TreeMap<String, String> paramMap = new TreeMap<>();
        String[] param = url.split("\\?");
        String[] parmaKey = param.length > 1 ? param[1].split("&") : (String[]) Arrays.asList(account).toArray();
        if (parmaKey.length >= 1 && param.length > 1) {
            for (String p : parmaKey) {
                String key = p.split("=")[0].replace("&", "").trim();
                String val = p.split("=")[1].replace("&", "").trim();
                paramMap.put(key.toLowerCase(), val);
            }
        } else {
            return HmacEncodeStr.getMd5Value(account);
        }

        StringBuffer sb = new StringBuffer("");
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            sb.append(",").append(entry.getKey()).append("=").append(entry.getValue());
        }
        String str = sb.toString().replaceFirst(",", "");
        return HmacEncodeStr.getMd5Value(str);
    }

}
