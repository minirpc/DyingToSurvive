package com.dyingtosurvive.rpccore.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by change-solider on 18-9-28.
 */
public class ClassUtils {
    public static Class getClassObject(Class clazz) {
        ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
        // 获取第一个类型参数的真实类型
        Type aa = pt.getActualTypeArguments()[0];
        System.out.println(aa.getTypeName());
        try {
            final Class zz = Class.forName(aa.getTypeName());
            System.out.println(zz.getName());
            System.out.println("aa");
            return zz;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
