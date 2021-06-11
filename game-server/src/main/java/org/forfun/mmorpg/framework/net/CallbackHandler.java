package org.forfun.mmorpg.framework.net;

import org.forfun.mmorpg.protocol.Message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CallbackHandler {

    private static ConcurrentMap<Integer, Callback> callbacks = new ConcurrentHashMap<>();

    public static void registerCallback(int index, Callback callback) {
        callbacks.put(index, callback);
    }

    public static Callback removeCallback(int index) {
        return callbacks.remove(index);
    }

    public static void fillCallback(int index, Message message) {
        Callback callback = callbacks.remove(index);
        if (callback != null) {
            if (!callback.getFuture().isDone()) {
                callback.getFuture().complete(message);
            }
        }
    }
}
