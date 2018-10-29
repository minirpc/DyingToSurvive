package com.dyingtosurvive.rpcmonitoragent.gather;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by change-solider on 18-10-29.
 */
public class TestRPCMonitorAgent {

    @Test
    public void testCollectJVMInfo() {
        RPCMonitorAgent agent = RPCMonitorAgent.build("127.0.0.1",8081);
        agent.startGather();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
