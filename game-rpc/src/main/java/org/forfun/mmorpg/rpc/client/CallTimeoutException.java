package org.forfun.mmorpg.rpc.client;

public class CallTimeoutException extends RuntimeException {

    public CallTimeoutException(String message) {
        super(message);
    }
}
