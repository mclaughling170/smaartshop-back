package com.param.smartsecurity.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;



/**
 * @Author Mc QiLing
 * @Date 2020/11/9 4:10 下午
 * @Version 1.0
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    /**
     * 创建时间
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LOG.info(" -------------------- start insert fill ...  --------------------");
        if (metaObject.hasGetter("createdAt") && metaObject.hasGetter("lastModified")) {
            this.strictInsertFill( metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill( metaObject, "lastModified", LocalDateTime.class, LocalDateTime.now());
        }
    }

    /**
     * 最后一次更新时间
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        LOG.info(" -------------------- start update fill ...  --------------------");
        if (metaObject.hasGetter("et.lastModified")) {
            this.strictInsertFill( metaObject, "lastModified", LocalDateTime.class, LocalDateTime.now());
        }
    }
    
}