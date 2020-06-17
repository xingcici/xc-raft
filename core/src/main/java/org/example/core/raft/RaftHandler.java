package org.example.core.raft;

import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcResponseFuture;
import org.example.common.life.LifeCycle;
import org.example.common.net.request.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : RaftHandler v0.1 2020/6/16 17:27 haifeng.pang Exp $
 **/
public class RaftHandler implements LifeCycle {
    private static Logger logger = LoggerFactory.getLogger(RaftHandler.class);

    private Map<String, String> connStatusMap = new HashMap<>();

    private RpcClient rpcClient;

    public RaftHandler(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    public void conn2OtherServer(String serverAddress, String selfAddress) {
        List<String> address = Arrays.asList(serverAddress.split(","));

        /*ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for (String addr : address) {
                    try {

                    }catch (Exception e) {
                        logger.error("发送心跳 建立连接异常 ", e);
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);*/


    }

    @Override
    public void startup() {

    }

    @Override
    public void shutdown() {

    }
}
