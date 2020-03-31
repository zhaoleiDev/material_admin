package com.zhaolei.material.admin.common.tools;

/**
 * @author ZHAOLEI
 */
public class TimeUtils {
    /**
     * 以秒为单位
     * jedis的setEx是以秒为单位的
     */
    public static final int TWO_MINUTE_S = 120;
    public static final int FIVE_MINUTE_S = 300;
    public static final int TEN_MINUTE_S = 600;

    /**
     * 以毫秒为单位
     * cookie的生存时间是以毫秒为单位的
     */
    public static final int EIGHT_HOURS_M = 28800;
    public static final int ONE_HOURS_M = 3600;
    public static final int ONE_DAY_M = 86400;

}
