package org.example.core.net.processor.event;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : ServerDisconnectProcessor v0.1 2020/6/17 17:26 haifeng.pang Exp $
 **/
public class DisconnectProcessor implements ConnectionEventProcessor {

    private String type;

    public DisconnectProcessor() {
    }

    public DisconnectProcessor(String type) {
        this.type = type;
    }

    @Override
    public void onEvent(String remoteAddress, Connection connection) {
        System.out.println(type + " disconnect event");
    }
}
