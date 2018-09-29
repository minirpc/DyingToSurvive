package com.dyingtosurvive.rpccore.db;

import java.util.List;
import java.util.Map;

/**
 * Created by change-solider on 18-9-27.
 */
public interface JDBCCallBack<T> {
    Integer insert(String sql);

    List<Map<String, Object>> select(String sql);

    Integer update(String sql);
}
