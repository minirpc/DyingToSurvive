package com.dyingtosurvive.rpccore.db;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.utils.ClassUtils;
import com.dyingtosurvive.rpcinterface.db.annotation.RPCField;
import com.dyingtosurvive.rpcinterface.db.annotation.RPCTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by change-solider on 18-9-27.
 */
public class DBUtils<T> {
    public Class<T> ref;
    private DBUtils<T> dbUtils;

    private DBUtils(Class<T> type) {
        this.ref = type;
    }

    public static <T> DBUtils<T> buildDBUtils(Class<T> tClass) {
        //todo 单例
        return new DBUtils<T>(tClass);
    }

    public List<T> parseResult(List<Map<String, Object>> result) {
        if (result == null || result.size() == 0) {
            return null;
        }
        List<T> objects = new ArrayList<T>();
        for (Map<String, Object> row : result) {
            objects.add(parseResult(row));
        }
        return objects;
    }

    public T parseResult(Map<String, Object> row) {
        if (row == null) {
            return null;
        }
        try {
            T t = ref.newInstance();
            Field[] fields = ref.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                RPCField fieldMeta = field.getAnnotation(RPCField.class);
                if (null == fieldMeta) {
                    continue;
                }
                String fieldName = fieldMeta.fieldName();
                String fieldType = fieldMeta.fieldType();
                //System.out.println("fieldName:" + fieldName);
                //System.out.println("fieldType:" + fieldType);
                if (row.containsKey(fieldName)) {
                    field.set(t, row.get(fieldName));
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateSelectSQL(Integer id) {
        if (id == null) {
            return null;
        }
        String tableName = getTableName();
        String sql = "select * from " + tableName + " where id = " + id;
        return sql;
    }

    private String getTableName() {
        RPCTable table = ref.getAnnotation(RPCTable.class);
        if (null == table) {
            System.out.println("没有配置表名，请检查");
            throw new IllegalStateException();
        }
        String tableName = table.tableName();
        return tableName;
    }

    private Map<String, RPCField> getFieldAndDefineMap() {
        Map<String, RPCField> rpcFieldMap = new HashMap<>();
        Field[] fields = ref.getDeclaredFields();
        for (Field field : fields) {
            RPCField fieldMeta = field.getAnnotation(RPCField.class);
            if (null == fieldMeta) {
                continue;
            }
            rpcFieldMap.put(field.getName(), fieldMeta);
        }
        return rpcFieldMap;
    }


    public String generateInsertSQL(T t) {
        String tableName = getTableName();
        String insertSQL = "insert into " + tableName + " ()";

        return insertSQL;
    }
}
