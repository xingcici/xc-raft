package org.example.core.net.client;

import com.alipay.remoting.CommonCommandCode;
import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.protocol.RpcProtocol;
import org.example.common.life.LifeCycle;
import org.example.core.net.processor.event.ConnectProcessor;
import org.example.core.net.processor.event.DisconnectProcessor;
import org.example.core.net.processor.event.HeartBeatProcessor;
import org.example.core.net.processor.user.sync.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : BoltClient v0.1 2020/6/17 18:00 haifeng.pang Exp $
 **/
public class BoltClient implements LifeCycle {

    private static Logger logger = LoggerFactory.getLogger(BoltClient.class);

    private String address;

    static RpcClient rpcClient;

    public BoltClient() {
    }

    public BoltClient(String address) {
        this.address = address;
    }

    @Override
    public void startup() {
        try {
            ConnectProcessor clientConnectProcessor = new ConnectProcessor("client");
            DisconnectProcessor clientDisconnectProcessor = new DisconnectProcessor("client");
            SyncClientUserProcessor syncClientUserProcessor = new SyncClientUserProcessor();
            HeartBeatProcessor heartBeatProcessor = new HeartBeatProcessor("client");
            rpcClient = new RpcClient();
            rpcClient.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectProcessor);
            rpcClient.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisconnectProcessor);
            rpcClient.registerUserProcessor(syncClientUserProcessor);
            rpcClient.startup();
            System.out.println("startup");
            rpcClient.createStandaloneConnection(address, 3000);
            System.out.println("createStandaloneConnection");
            System.out.println("getConnection");
            rpcClient.enableConnHeartbeat(address);
            //System.out.println("enableConnHeartbeat");
            //System.out.println(client.invokeSync(raftAddress, new RequestBody(UUID.randomUUID().toString(), "hello iam client", "".getBytes()), 3000));
            //System.out.println("invokeSync");
        }catch (Exception e) {
            logger.error("建立连接异常 ", e);
        }
    }

    @Override
    public void shutdown() {
        if (null != rpcClient) {
            rpcClient.shutdown();
        }
    }

    public RpcClient getRpcClient() {
        return rpcClient;
    }
}
