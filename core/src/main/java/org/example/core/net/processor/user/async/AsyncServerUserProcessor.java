package org.example.core.net.processor.user.async;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.NamedThreadFactory;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import org.example.common.net.request.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : ServerUserProcessor v0.1 2020/6/16 17:36 haifeng.pang Exp $
 **/
public class AsyncServerUserProcessor extends AsyncUserProcessor<RequestBody> {

    private static Logger logger = LoggerFactory.getLogger(AsyncServerUserProcessor.class);

    private ThreadPoolExecutor asyncExecutor;

    public AsyncServerUserProcessor() {
        this.asyncExecutor = new ThreadPoolExecutor(1, 3, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new NamedThreadFactory(
                "Another-aysnc-process-pool"));
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, RequestBody request) {
        this.asyncExecutor.execute(new AsyncServerUserProcessor.InnerTask(asyncCtx, request));
    }

    class InnerTask implements Runnable {
        private AsyncContext asyncCtx;
        private RequestBody  request;

        public InnerTask(AsyncContext asyncCtx, RequestBody request) {
            this.asyncCtx = asyncCtx;
            this.request = request;
        }

        @Override
        public void run() {
            System.out.println("Request server received:" + request.toString());

            this.asyncCtx.sendResponse("heart back");

        }
    }

    @Override
    public String interest() {
        return RequestBody.class.getName();
    }
}
