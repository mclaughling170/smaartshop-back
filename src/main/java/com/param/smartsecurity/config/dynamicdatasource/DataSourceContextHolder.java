package com.param.smartsecurity.config.dynamicdatasource;

/**
 * @Author Mc QiLing
 * @Date 2020/11/9 4:10 下午
 * @Version 1.0
 */
public class DataSourceContextHolder {

    /**
     * //开启多个线程，每个线程初始化一个数据源
     */
    private static final ThreadLocal contextHolder = new ThreadLocal<>();
    /**
     * 设置数据源
     * @param dbTypeEnum
     */
    public static void setDbType(CommonEnum dbTypeEnum) {
        contextHolder.set(dbTypeEnum.getValue());
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDbType() {
        return (String) contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
    }

}