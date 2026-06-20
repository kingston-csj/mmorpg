package org.forfun.mmorpg.game.exception;

/**
 * 业务请求执行异常
 */
public class BusinessRequestException extends RuntimeException {

    /**
     * 异常状态吗{@link I18nConstants}
     */
    private int errorCode;

    public BusinessRequestException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}