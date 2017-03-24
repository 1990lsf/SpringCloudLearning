package com.springframework.wsdl;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/2/28
 * @see CallHandler
 * @since 1.0
 */
@FunctionalInterface
public interface CallHandler {
    /**
     * 处理相应状态
     * @param callResult 请求结果
     */
    void call(boolean callResult);
}
