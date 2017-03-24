package com.springframework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import java.util.Map;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/2/28
 * @see JdbcUserDetailsService
 * @since 1.0
 */
@Service
public class JdbcUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDetailsService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        if(logger.isDebugEnabled()){
//            logger.debug("[授权服务] - [请求用户:{}]",userName);
//        }
        logger.info("[授权服务] - [请求用户:{}]",userName);
		/*~note: not support permission**/
        final String userDetailSql = "SELECT u.id userId, u.is_lock,u.password FROM p_user u WHERE u.username = ?" ;
        try{
            Map<String, Object> userMap = jdbcTemplate.queryForMap(userDetailSql,new Object[]{userName});
            return new User(
                    userName, //用户
                    userMap.get("password").toString(),//密码
                    Lists.newArrayList());//角色
        }catch(EmptyResultDataAccessException e){
            if(logger.isDebugEnabled()){
                logger.debug("[授权服务] - [请求用户:{}] - [sql:{}] 没有找到",userName,userDetailSql);
            }
            throw new UsernameNotFoundException(userName + " user not found ");
        }
    }

}
