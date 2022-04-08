package edu.hevttc.express.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据隐私显示
 */
public class MaskNameAndTelInfoUtil {
    // 用户名敏感符
    private static final String NAME_MASK = "**";
    // 电话敏感符
    private static final String TEL_MASK = "****";
    // 用户名脱敏起始位置
    private static final int NAME_START = 1;
    // 电话脱敏起始位置
    private static final int TEL_START = 5;
    // 电话脱敏终止位置
    private static final int TEL_END = 9;

    /**
     * 用户名脱敏
     *
     * @param realName 真实用户名：张三
     * @return 脱敏后用户名：张**
     */
    public static String maskName(String realName) {
        if (StringUtils.isEmpty(realName)) {
            return "";
        } else {
            return StringUtils.overlay(realName, NAME_MASK, NAME_START, realName.length());
        }
    }

    /**
     * 电话脱敏
     *
     * @param realTel 真实电话号：15311112222
     * @return 脱敏后电话号：15211****22
     */
    public static String maskTel(String realTel) {
        if (StringUtils.isEmpty(realTel)) {
            return "";
        } else {
            return StringUtils.overlay(realTel, TEL_MASK, TEL_START, TEL_END);
        }
    }
}
