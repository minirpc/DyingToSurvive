package com.dyingtosurvive.rpcconfig.dao;

import com.dyingtosurvive.rpccore.db.AbstractBaseDAO;
import com.dyingtosurvive.rpccore.db.JDBCCallBack;
import com.dyingtosurvive.rpcinterface.model.ServiceWeight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * 　模板方法模式
 * <p>
 * AbstractBaseDAO控制主要流程，BaseDAO需要提供jdbctemplate的实现
 * <p>
 * <p>
 * Created by change-solider on 18-9-27.
 */
@Repository
public class BaseDAO<T> extends AbstractBaseDAO<T> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected JDBCCallBack<T> injectJDBCCallBack() {
        return new JDBCCallBack<T>() {
            @Override
            public Integer insert(final String sql) {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        return statement;
                    }
                }, keyHolder);
                return keyHolder.getKey().intValue();
            }

            @Override
            public List<Map<String, Object>> select(String sql) {
                List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                return result;
            }

            @Override
            public Integer update(final String sql) {
                System.out.println("aaa");
                return jdbcTemplate.update(sql);
            }
        };
    }
}
