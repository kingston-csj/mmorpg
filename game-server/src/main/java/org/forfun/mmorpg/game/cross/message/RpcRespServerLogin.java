package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.MessageSource;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

@MessageMeta(cmd = ConstantCross.CMD_RESP_SERVER_LOGIN, source = MessageSource.FROM_SERVER_TO_SERVER)
public class RpcRespServerLogin  implements Message {

    private int remoteSid;

    private int serverType;

    public int getRemoteSid() {
        return remoteSid;
    }

    public void setRemoteSid(int remoteSid) {
        this.remoteSid = remoteSid;
    }

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

}
