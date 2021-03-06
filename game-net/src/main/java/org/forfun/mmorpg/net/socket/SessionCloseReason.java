package org.forfun.mmorpg.net.socket;

public enum SessionCloseReason {

    /**
     * 正常退出
     */
    NORMAL,

    /**
     * 链接超时
     */
    OVER_TIME,

    /**
     * 非法登录
     */
    ILLEGAL_LOGIN,

}
