package com.param.smartsecurity.config.dynamicdatasource;

/**
 * @Author Mc QiLing
 * @Date 2020/11/9 4:10 下午
 * @Version 1.0
 */
public enum CommonEnum {

    /**
     * 主库
     */
    DB1("master"),

    /**
     * 从库一
     */
    DB2("db2");

    private  String value;

    CommonEnum(String value) {
        this.value = value;
    }

    public  String getValue() {
        return value;
    }

}
