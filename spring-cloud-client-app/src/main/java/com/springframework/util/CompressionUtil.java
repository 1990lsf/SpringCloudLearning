package com.springframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/21
 * @see CompressionUtil
 * @since 1.0
 */
public class CompressionUtil {
    private static Logger logger = LoggerFactory.getLogger(CompressionUtil.class);

    /**
     * 压缩数据
     * @param object
     * @return
     */
    public static byte[] compress(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("压缩报错", e);
            }
        }
        return null;
    }

    /**
     * 解压数据
     * @param compressByte
     * @return
     */
    public static Object uncompress(byte[] compressByte) {
        ObjectInputStream objectIn = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(compressByte);
            GZIPInputStream gzipIn = new GZIPInputStream(bais);
            objectIn = new ObjectInputStream(gzipIn);
            return objectIn.readObject();
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("解压缩报错", e);
            }
        } catch (ClassNotFoundException e) {
            if (logger.isErrorEnabled()) {
                logger.error("文件没有找到", e);
            }
        } finally {
            if (null != objectIn) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("文件流关闭失败", e);
                    }
                }
            }
        }
        return null;
    }


    public static void main(String[] args){
        String object="12345QAZWSXFFEF";
        byte[] bytes=CompressionUtil.compress(object);
        logger.info("压缩结果{}",bytes);
        Object ob=CompressionUtil.uncompress(bytes);
        logger.info("解压结果{}",ob.toString());
    }
}
