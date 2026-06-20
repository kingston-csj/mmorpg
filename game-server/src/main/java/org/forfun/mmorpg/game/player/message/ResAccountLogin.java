package org.forfun.mmorpg.game.player.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.player.message.vo.PlayerLoginVo;
import org.forfun.mmorpg.game.player.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

@MessageMeta(cmd = PlayerService.CMD_RES_ACCOUNT_LOGIN)
public class ResAccountLogin implements Message {

    @Protobuf
    private List<PlayerLoginVo> players = new ArrayList<>();

    public List<PlayerLoginVo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerLoginVo> players) {
        this.players = players;
    }
}
