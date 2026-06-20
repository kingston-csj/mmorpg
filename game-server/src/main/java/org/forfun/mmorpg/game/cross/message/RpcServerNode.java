package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

import java.util.Objects;

@MessageMeta(cmd = ConstantCross.CMD_RPC_NODE)
public class RpcServerNode implements Message {

    private int sid;

    /**
     * {@link org.forfun.mmorpg.game.ServerType#type}
     */
    private int type;

    private String ip;

    private int port;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcServerNode that = (RpcServerNode) o;
        return sid == that.sid && type == that.type && port == that.port && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid, type, ip, port);
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
