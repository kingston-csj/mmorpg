package org.forfun.mmorpg.framework.net;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;

@Getter
@Setter
public class Callback {

    /**
     * 超时任务
     */
    protected ScheduledFuture timeout;

    protected CompletableFuture future;
}
