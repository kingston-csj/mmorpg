package org.forfun.mmorpg.rpc.client;

public interface RequestCallback {

    /**
     * 请求方接受回调消息的业务处理
     *
     * @param callBack
     */
    void onSuccess(Object callBack);

    void onError(Throwable error);

}
