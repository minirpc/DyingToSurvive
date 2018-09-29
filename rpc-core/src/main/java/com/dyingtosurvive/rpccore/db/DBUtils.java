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


    private Map<String, Object> getFieldAndValueMap(T t) {
        try {
            Map<String, Object> fieldAndValueMap = new HashMap<>();
            Map<String, RPCField> rpcFieldMap = getFieldAndDefineMap();
            for (Map.Entry<String, RPCField> entry : rpcFieldMap.entrySet()) {
                String key = entry.getKey();
                RPCField rpcField = entry.getValue();
                if (rpcField.isPrimaryKey()) {
                    //是主键则下一个
                    continue;
                }
                Field field = ref.getDeclaredField(key);
                field.setAccessible(true);
                Object value = field.get(t);
                if (value != null) {
                    fieldAndValueMap.put(key, value);
                }
            }
            return fieldAndValueMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("生成插入语句失败");
        }
    }

    public String generateInsertSQL(T t) {
        String tableName = getTableName();
        String insertSQL = "insert into " + tableName + " (";

        Map<String, RPCField> rpcFieldMap = getFieldAndDefineMap();
        Map<String, Object> fieldAndValueMap = getFieldAndValueMap(t);

        for (Map.Entry<String, Object> entry : fieldAndValueMap.entrySet()) {
            insertSQL = insertSQL.concat(rpcFieldMap.get(entry.getKey()).fieldName()).concat(",");
        }
        insertSQL = insertSQL.substring(0, insertSQL.length() - 1);
        insertSQL = insertSQL.concat(") values(");
        for (Map.Entry<String, Object> entry : fieldAndValueMap.entrySet()) {
            if ("String".equals(rpcFieldMap.get(entry.getKey()).fieldType())) {
                String value = (String) entry.getValue();
                insertSQL = insertSQL.concat("\"").concat(value).concat("\"").concat(",");
            } else if ("Integer".equals(rpcFieldMap.get(entry.getKey()).fieldType())) {
                Integer value = (Integer) entry.getValue();
                insertSQL = insertSQL.concat("" + value).concat(",");
            } else if ("Long".equals(rpcFieldMap.get(entry.getKey()).fieldType())) {
                Long value = (Long) entry.getValue();
                insertSQL = insertSQL.concat("" + value).concat(",");
            }
        }
        insertSQL = insertSQL.substring(0, insertSQL.length() - 1);
        insertSQL = insertSQL.concat(");");
        System.out.println("insertSQL:" + insertSQL);
        return insertSQL;
    }

    public Integer getPrimaryKeyValue(T t) {
        try {
            Map<String, RPCField> rpcFieldMap = getFieldAndDefineMap();
            for (Map.Entry<String, RPCField> entry : rpcFieldMap.entrySet()) {
                RPCField rpcField = entry.getValue();
                if (!rpcField.isPrimaryKey()) {
                    //是主键则下一个
                    continue;
                }
                Field field = ref.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                Integer value = (Integer) field.get(t);
                return value;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("生成插入语句失败");
        }
    }

    public String generateUpdateByPrimaryKeySQL(T t) {
        String sql = "update " + getTableName() + "  set ";
        Map<String, Object> fieldAndValueMap = getFieldAndValueMap(t);
        Map<String, RPCField> rpcFieldMap = getFieldAndDefineMap();
        for (Map.Entry<String, Object> entry : fieldAndValueMap.entrySet()) {
            sql = sql.concat(rpcFieldMap.get(entry.getKey()).fieldName()).concat(" = ");
            sql = sql.concat("\"").concat((String) entry.getValue()).concat("\"");
        }
        sql = sql.concat(" where id = " + getPrimaryKeyValue(t)).concat(";");
        return sql;
    }

    public String must(String applicationName) {
        return " = \"" + applicationName + "\"";
    }

    public String generateSelectSQL(Map<String, String> params) {
        String sql = "select * from " + getTableName();
        if (params == null || params.size() == 0) {
            return sql;
        }
        sql = sql.concat(" where ");
        Map<String, RPCField> rpcFieldMap = getFieldAndDefineMap();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String fieldName = rpcFieldMap.get(key).fieldName();
            sql = sql.concat(fieldName).concat(entry.getValue());
        }
        return sql;
    }
}
