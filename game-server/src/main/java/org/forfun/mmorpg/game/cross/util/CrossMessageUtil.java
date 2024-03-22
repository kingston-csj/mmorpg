package org.forfun.mmorpg.game.cross.util;

import jforgame.socket.share.IdSession;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.service.RpcClientRouter;
import org.forfun.mmorpg.game.logger.LoggerUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class CrossMessageUtil {

    private static AtomicInteger idFactory = new AtomicInteger(1);

    public static void requestToCenter(Message message) {
        RpcClientRouter clientRouter = GameContext.getBean(RpcClientRouter.class);
        IdSession session = clientRouter.getCenterSession();
        if (session == null) {
            LoggerUtils.error("中心服链路不可达");
            return;
        }
        session.send(message);
    }

//
//    public static CompletableFuture<RpcCallbackResponse> requestToCenter(RpcCallbackRequest message) {
//        RpcClientRouter clientRouter = GameContext.getBean(RpcClientRouter.class);
//        IdSession session = clientRouter.getCenterSession();
//        if (session == null) {
//            throw new IllegalArgumentException("中心服链路不可达");
//        }
//
//        int index = idFactory.getAndIncrement();
//        message.setCallbackId(index);
//
//        CompletableFuture future = new CompletableFuture();
//        Callback callback = new Callback();
//        callback.setFuture(future);
//        CallbackHandler.registerCallback(index, callback);
//
//        ScheduledFuture timeout = GameContext.getSchedulerManager().schedule(() -> {
//            LoggerUtils.error("跨服消息[{}]回调超时", message.getClass().getSimpleName());
//            CallbackHandler.removeCallback(index);
//            callback.getFuture().completeExceptionally(new RpcTimeoutException());
//        }, Callback.TIME_OUT);
//        callback.setTimeout(timeout);
//
//        session.sendPacket(message);
//
//        return future;
//    }

}
