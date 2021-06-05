package org.forfun.mmorpg.framework.net;

import org.forfun.mmorpg.protocol.Message;

import java.util.concurrent.ScheduledFuture;

public abstract class Callback {

    /**
     * 超时任务
     */
    protected ScheduledFuture future;

    /**
     * 请求方接受回调消息的业务处理
     * @param callBack
     */
    public abstract void onMessageReceive(Message callBack);

    public abstract void onError();

    public ScheduledFuture getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }
}
