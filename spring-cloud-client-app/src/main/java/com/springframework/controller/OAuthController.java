package com.springframework.controller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/23
 * @see OAuthController
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/thirdpart")
public class OAuthController {
    private final String client_id = "whc_client_id";
    private final String response_type = "code";
    private final String oauthServer_getCode_url = "http://localhost:8080/oauthserver/get-code";
    private final String redirect_url = "http://localhost:8080/thirdpart/token";
    @RequestMapping(value = "/code",method = RequestMethod.GET)
    private void code()throws IOException,OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(oauthServer_getCode_url)
                .setClientId(client_id)
                .setRedirectURI(redirect_url)
                .setResponseType(response_type)
                .buildQueryMessage();

        GetMethod getMethod=new GetMethod(request.getLocationUri());
        getMethod.setFollowRedirects(true);

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(getMethod);
    }

}
