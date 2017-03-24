package com.springframework.remoteTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springframework.SpringCloudClientAppApplication;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/9
 * @see RedisTest
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringCloudClientAppApplication.class)

public class RedisTest {
    private static final Logger logger= LoggerFactory.getLogger(RemoteHandlerTest.class);

    @Test
    public void redisTest(){

    }
}
