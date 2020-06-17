package org.example.core.net.server;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.rpc.RpcServer;
import org.example.common.life.LifeCycle;
import org.example.core.net.processor.event.ConnectProcessor;
import org.example.core.net.processor.event.DisconnectProcessor;
import org.example.core.net.processor.user.sync.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : BoltServer v0.1 2020/6/17 18:01 haifeng.pang Exp $
 **/
public class BoltServer implements LifeCycle {

    private static Logger logger = LoggerFactory.getLogger(BoltServer.class);

    private int port = 9876;

    static RpcServer rpcServer;

    public BoltServer() {
    }

    public BoltServer(int port) {
        this.port = port;
    }

    @Override
    public void startup() {
        ConnectProcessor serverConnectProcessor = new ConnectProcessor("server");
        DisconnectProcessor serverDisconnectProcessor = new DisconnectProcessor("server");
        SyncServerUserProcessor syncServerUserProcessor = new SyncServerUserProcessor();

        rpcServer = new RpcServer(port, true);
        rpcServer.startup();
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        rpcServer.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisconnectProcessor);
        rpcServer.registerUserProcessor(syncServerUserProcessor);
    }

    @Override
    public void shutdown() {
        if (null != rpcServer) {
            rpcServer.shutdown();
        }
    }
}
