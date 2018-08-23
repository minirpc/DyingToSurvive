package com.dyingtosurvive.rpccore;

import static org.junit.Assert.assertTrue;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

public class AppTest {
    public static void main(String[] args) {
        //String retData = Unirest.get(url).queryString("name", name).asJson().getBody().getObject();

        try {
            System.out.println(
                Unirest.get("http://www.baidu.com").asJson());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }
}
