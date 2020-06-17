package org.example.core.net.processor.user.sync;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import org.example.common.net.request.RequestBody;

import java.util.UUID;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : SyncServerUserProcessor v0.1 2020/6/16 18:41 haifeng.pang Exp $
 **/
public class SyncServerUserProcessor extends SyncUserProcessor<RequestBody> {
    @Override
    public Object handleRequest(BizContext bizCtx, RequestBody request) throws Exception {
        System.out.println("收到" + bizCtx.getRemoteAddress() + "客户端信息 " + request.getMsg());
        return new RequestBody(UUID.randomUUID().toString(), "this is Server", "".getBytes());
    }

    @Override
    public String interest() {
        return RequestBody.class.getName();
    }
}
