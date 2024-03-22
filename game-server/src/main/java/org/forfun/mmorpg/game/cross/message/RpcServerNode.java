package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

import java.util.Objects;

@Getter
@Setter
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
}
