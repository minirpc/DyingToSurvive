package com.dyingtosurvive.rpcconfig.dao;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcconfig.ServerApplication;
import com.dyingtosurvive.rpcinterface.model.ServiceWeight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by change-solider on 18-9-26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles("test")
public class TestWeightDAO {
    @Autowired
    private IWeightDAO weightDAO;

    @Test
    public void testSaveData() {
        Integer id = weightDAO.saveServiceWeight(null);
        System.out.println(id);
    }

    @Test
    public void testSelectData() {
        ServiceWeight serviceWeight = weightDAO.selectByPrimaryKey(2);
        System.out.println(JSONObject.toJSONString(serviceWeight));
    }
}
