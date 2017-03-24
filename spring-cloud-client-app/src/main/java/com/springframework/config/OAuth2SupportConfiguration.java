package com.springframework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/2/28
 * @see OAuth2SupportConfiguration
 * @since 1.0
 */
@Configuration
public class OAuth2SupportConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2SupportConfiguration.class);
    @Autowired
    private Environment environment;



    @Bean
    protected OAuth2ProtectedResourceDetails resource(){
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setUsername(environment.getProperty("oauth.username"));
        resource.setPassword(environment.getProperty("oauth.password"));
        resource.setAccessTokenUri(environment.getProperty("oauth.gateway")+"oauth/token");
        resource.setClientId(environment.getProperty("oauth.clientId"));
        resource.setGrantType(environment.getProperty("oauth.grantType"));
        resource.setClientSecret(environment.getProperty("oauth.clientPwd"));
        List<String> scopeList= new ArrayList();
        scopeList.add(environment.getProperty("oauth.scope"));
        resource.setScope(scopeList);
        logger.info("授权信息-username:{},password:{},accessTokeUri:{},clientId:{},grantType:{},clientSecret:{},scope:{}",
                resource.getUsername(),resource.getPassword(),resource.getAccessTokenUri(),resource.getClientId(),resource.getGrantType(),resource.getClientSecret(),
                resource.getScope());
        return resource;
    }

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(){
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setUsername(environment.getProperty("oauth.username"));
        resource.setPassword(environment.getProperty("oauth.password"));
        resource.setAccessTokenUri(environment.getProperty("oauth.token.url")+"oauth/token");
        resource.setClientId(environment.getProperty("oauth.clientId"));
        resource.setGrantType(environment.getProperty("oauth.grantType"));
        resource.setClientSecret(environment.getProperty("oauth.clientPwd"));
        List<String> scopeList= new ArrayList();
        scopeList.add(environment.getProperty("oauth.scope"));
        resource.setScope(scopeList);

        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(atr));
        logger.info("授权信息-accessTokeUri:{},clientId:{},grantType:{},clientSecret:{},scope:{}",
                resource.getAccessTokenUri(),resource.getClientId(),resource.getGrantType(),resource.getClientSecret(),
                resource.getScope());
        return restTemplate;

    }
}
