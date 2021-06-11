package org.forfun.mmorpg.game.cross.util;

import org.forfun.mmorpg.framework.net.Callback;
import org.forfun.mmorpg.framework.net.CallbackHandler;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.service.RpcClientRouter;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.net.rpc.RpcCallbackRequest;
import org.forfun.mmorpg.net.rpc.RpcCallbackResponse;
import org.forfun.mmorpg.net.rpc.RpcTimeoutException;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.Message;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class CrossMessageUtil {

    private static AtomicInteger idFactory = new AtomicInteger(1);

    public static void sendToCenter(Message message) {
        RpcClientRouter clientRouter = GameContext.getBean(RpcClientRouter.class);
        IdSession session = clientRouter.getCenterSession();
        if (session == null) {
            LoggerUtils.error("中心服链路不可达");
            return;
        }
        session.sendPacket(message);
    }


    public static CompletableFuture<RpcCallbackResponse> sendToCenter(RpcCallbackRequest message) {
        RpcClientRouter clientRouter = GameContext.getBean(RpcClientRouter.class);
        IdSession session = clientRouter.getCenterSession();
        if (session == null) {
            throw new IllegalArgumentException("中心服链路不可达");
        }

        int index = idFactory.getAndIncrement();
        message.setCallbackId(index);

        CompletableFuture future = new CompletableFuture();

        Callback callback = new Callback();
        callback.setFuture(future);
        CallbackHandler.registerCallback(index, callback);

        ScheduledFuture timeout = GameContext.getSchedulerManager().schedule(() -> {
            LoggerUtils.error("跨服消息[{}]回调超时", message.getClass().getSimpleName());
            CallbackHandler.removeCallback(index);
            callback.getFuture().completeExceptionally(new RpcTimeoutException());

        }, 5000);
        callback.setTimeout(timeout);

        session.sendPacket(message);

        return future;
    }

}
