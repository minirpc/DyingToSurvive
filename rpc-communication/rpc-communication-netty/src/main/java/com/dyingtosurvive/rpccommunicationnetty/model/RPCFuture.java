package com.dyingtosurvive.rpccommunicationnetty.model;

import com.dyingtosurvive.rpccore.communication.RPCResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用future设计模式，异步获取请求结果　
 *
 * 使用socket自然会遇到client读取server的返回结果，采用future可以有结果后返回，没有结果则等待
 *
 * Created by change-solider on 18-10-29.
 */
public class RPCFuture {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private volatile RPCResponse rpcResponse;

    public void setRPCResponse(RPCResponse rpcResponse) {
        try {
            lock.lock();
            this.rpcResponse = rpcResponse;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }


    public RPCResponse get() {
        try {
            lock.lock();
            if (rpcResponse == null) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return rpcResponse;
        } finally {
            lock.unlock();
        }
    }
}
