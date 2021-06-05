package org.forfun.mmorpg.game.cross.util;

import org.forfun.mmorpg.framework.net.Callback;
import org.forfun.mmorpg.framework.net.CallbackHandler;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.service.RpcClientRouter;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.net.rpc.RpcCallbackMessage;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.Message;

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
    }

    public static void sendToCenter(RpcCallbackMessage message, Callback callBack) {
        RpcClientRouter clientRouter = GameContext.getBean(RpcClientRouter.class);
        IdSession session = clientRouter.getCenterSession();
        if (session == null) {
            throw new IllegalArgumentException("中心服链路不可达");
        }
        int index = idFactory.getAndIncrement();
        message.setIndex(index);
        CallbackHandler.registerCallback(index, callBack);
        session.sendPacket(message);
        ScheduledFuture future = GameContext.getSchedulerManager().schedule(() -> {
            LoggerUtils.error("跨服消息回调超时", message.getClass().getSimpleName());
            callBack.onError();
            CallbackHandler.removeCallback(index);
        }, 5000);
        callBack.setFuture(future);
    }
}
