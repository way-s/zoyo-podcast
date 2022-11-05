package com.zoyo.web.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: mxx
 * @Description: des 加密工具类
 */
@Slf4j
public class EncryptionUtil {

    /**
     * key
     */
    private static final String KEY = "abnSQyYo2p1WX9dSWVc";

    /**
     * 生成 des
     */
    private static final DES DES = SecureUtil.des(
            SecureUtil.generateKey(
                    SymmetricAlgorithm.DES.getValue(), KEY.getBytes()
            ).getEncoded()
    );

    /**
     * 加密
     *
     * @param content 加密字符
     * @return String
     */
    public static String encrypt(String content) {
        try {
            return DES.encryptHex(content);
        } catch (Exception e) {
            log.error("des encrypt 加密错误 ，原因是 {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param content 需要解密字符
     * @return String
     */
    public static String decryptStr(String content) {
        try {
            return DES.decryptStr(content);
        } catch (Exception e) {
            log.error("des decryptStr 解密错误 ，原因是 {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
