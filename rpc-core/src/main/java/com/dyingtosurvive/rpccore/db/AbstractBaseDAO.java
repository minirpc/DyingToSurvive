package com.dyingtosurvive.rpccore.db;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 抽象的数据库访问操作类
 * <p>
 * Created by change-solider on 18-9-27.
 */
public abstract class AbstractBaseDAO<T> {
    private volatile JDBCCallBack<T> jdbcCallBack;

    protected abstract JDBCCallBack injectJDBCCallBack();

    protected Integer save(T t) {
        DBUtils<T> dbUtils = DBUtils.buildDBUtils(ClassUtils.getClassObject(this.getClass()));
        String sql = dbUtils.generateInsertSQL(t);
        if (jdbcCallBack == null) {
            jdbcCallBack = injectJDBCCallBack();
        }
        return jdbcCallBack.insert(sql);
    }

    protected T getByPrimaryKey(Integer id) {
        DBUtils<T> dbUtils = DBUtils.buildDBUtils(ClassUtils.getClassObject(this.getClass()));
        String sql = dbUtils.generateSelectSQL(id);
        if (jdbcCallBack == null) {
            jdbcCallBack = injectJDBCCallBack();
        }
        List<Map<String, Object>> result = jdbcCallBack.select(sql);
        if (result == null || result.size() == 0) {
            return null;
        }
        if (result.size() > 1) {
            //todo 异常开发
            System.out.println("根据id查询出了多条记录，请检查!");
            throw new IllegalStateException();
        }
        return dbUtils.parseResult(result.get(0));
    }

    protected List<T> selectForList(Map<String, String> params) {
        DBUtils<T> dbUtils = DBUtils.buildDBUtils(ClassUtils.getClassObject(this.getClass()));
        String sql = dbUtils.generateSelectSQL(params);
        if (jdbcCallBack == null) {
            jdbcCallBack = injectJDBCCallBack();
        }
        List<Map<String, Object>> result = jdbcCallBack.select(sql);
        if (result == null || result.size() == 0) {
            return null;
        }
        return dbUtils.parseResult(result);
    }

    protected Integer updateByPrimaryKey(T t) {
        DBUtils<T> dbUtils = DBUtils.buildDBUtils(ClassUtils.getClassObject(this.getClass()));
        String sql = dbUtils.generateUpdateByPrimaryKeySQL(t);
        if (jdbcCallBack == null) {
            jdbcCallBack = injectJDBCCallBack();
        }
        jdbcCallBack.update(sql);
        return dbUtils.getPrimaryKeyValue(t);
    }
}
