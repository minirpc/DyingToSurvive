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

import java.util.List;

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
        ServiceWeight serviceWeight = new ServiceWeight();
        serviceWeight.setApplicationName("rpc-config");
        serviceWeight.setCreatedBy("lizi");
        serviceWeight.setServiceInterface("rpcservicewwgiss");
        serviceWeight.setServiceName("rpcservice");
        serviceWeight.setUpdatedBy("zhibing.chen");
        serviceWeight.setWeight(200);
        Integer id = weightDAO.saveServiceWeight(serviceWeight);
        System.out.println(id);
    }

    @Test
    public void testSelectData() {
        ServiceWeight serviceWeight = weightDAO.selectByPrimaryKey(2);
        System.out.println(JSONObject.toJSONString(serviceWeight));
    }

    @Test
    public void testUpdateByPrimaryKey() {
        //ServiceWeight serviceWeight = weightDAO.selectByPrimaryKey(2);
        ServiceWeight serviceWeight = new ServiceWeight();
        serviceWeight.setId(2);
        serviceWeight.setCreatedBy("zhiqiang");
        Integer id = weightDAO.updateServiceWeightByPrimaryKey(serviceWeight);
        System.out.println(id);
    }


    @Test
    public void testSelectForList() {
        List<ServiceWeight> serviceWeights = weightDAO.selectForList("rpc-config", null, null, null, null);
        System.out.println(JSONObject.toJSONString(serviceWeights));
    }

}
