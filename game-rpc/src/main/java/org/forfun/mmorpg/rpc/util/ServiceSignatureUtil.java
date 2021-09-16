package org.forfun.mmorpg.rpc.util;

public class ServiceSignatureUtil {

    public static String makeSignature(String serviceName, String methodName) {
        return  serviceName + "#" + methodName;
    }
}
