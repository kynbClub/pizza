package org.pizza.mybatis.plus.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author 高巍
 * @since 2019年06月27日 14:18
 */

public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName("createTime", metaObject);
        Object createUser = getFieldValByName("createUser", metaObject);
        Object tenantId = getFieldValByName("tenantId", metaObject);
        Object deleted = getFieldValByName("deleted", metaObject);
        if (createTime == null) {
            this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        }
        if (createUser == null) {
            this.strictInsertFill(metaObject, "createUser", Long.class, 0L);
        }
        if (tenantId == null) {
            this.strictInsertFill(metaObject, "tenantId", Long.class, 0L);
        }
        if (deleted == null) {
            this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        }
        this.updateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        Object updateUser = getFieldValByName("updateUser", metaObject);
        if (updateTime == null) {
            this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        }
        if (updateUser == null) {
            this.strictInsertFill(metaObject, "updateUser", Long.class, 0L);
        }

    }
}
