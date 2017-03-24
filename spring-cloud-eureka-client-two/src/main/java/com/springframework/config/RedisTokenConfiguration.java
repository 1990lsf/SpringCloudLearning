package com.springframework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/23
 * @see RedisTokenConfiguration
 * @since 1.0
 */
@Configuration
public class RedisTokenConfiguration {
    @Autowired
    private RedisConnectionFactory redisConnectionFactroy;
    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactroy);
    }
}
