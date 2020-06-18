package org.example.core.net.processor.event;

import com.alipay.remoting.*;
import com.alipay.remoting.log.BoltLoggerFactory;
import com.alipay.remoting.rpc.HeartbeatAckCommand;
import com.alipay.remoting.rpc.HeartbeatCommand;
import com.alipay.remoting.rpc.protocol.RpcHeartBeatProcessor;
import com.alipay.remoting.util.RemotingUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : HeartBeatProcessor v0.1 2020/6/18 09:42 haifeng.pang Exp $
 **/
public class HeartBeatProcessor extends RpcHeartBeatProcessor {

    private static final Logger logger = BoltLoggerFactory.getLogger("RpcRemoting");

    private String type;

    public HeartBeatProcessor() {

    }

    public HeartBeatProcessor(String type) {
        this.type = type;
    }

    @Override
    public void doProcess(RemotingContext ctx, RemotingCommand msg) {
        if (msg instanceof HeartbeatCommand) {
            System.out.println( type + " 收到心跳" + RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
        }

        if (msg instanceof HeartbeatAckCommand) {
            System.out.println(type + " 收到心跳确认" + RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
        }
        // process the heartbeat
        if (msg instanceof HeartbeatCommand) {
            final int id = msg.getId();
            if (logger.isDebugEnabled()) {
                logger.debug("Heartbeat received! Id=" + id + ", from "
                        + RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
            }
            HeartbeatAckCommand ack = new HeartbeatAckCommand();
            ack.setId(id);
            ctx.writeAndFlush(ack).addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Send heartbeat ack done! Id={}, to remoteAddr={}", id,
                                    RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
                        }
                    } else {
                        logger.error("Send heartbeat ack failed! Id={}, to remoteAddr={}", id,
                                RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
                    }
                }

            });
        } else if (msg instanceof HeartbeatAckCommand) {
            Connection conn = ctx.getChannelContext().channel().attr(Connection.CONNECTION).get();
            InvokeFuture future = conn.removeInvokeFuture(msg.getId());
            if (future != null) {
                future.putResponse(msg);
                future.cancelTimeout();
                try {
                    future.executeInvokeCallback();
                } catch (Exception e) {
                    logger.error(
                            "Exception caught when executing heartbeat invoke callback. From {}",
                            RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()), e);
                }
            } else {
                logger
                        .warn(
                                "Cannot find heartbeat InvokeFuture, maybe already timeout. Id={}, From {}",
                                msg.getId(),
                                RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
            }
        } else {
            throw new RuntimeException("Cannot process command: " + msg.getClass().getName());
        }
    }
}
