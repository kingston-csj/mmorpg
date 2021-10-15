package org.forfun.mmorpg.game;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.message.Rpc_G2C_FetchFightServerNodes;
import org.forfun.mmorpg.game.cross.service.RpcClientRouter;
import org.forfun.mmorpg.net.socket.IdSession;

public class GameServerLayer implements ServerLayer {

    @Override
    public void init() {
//        VipRight vipRight = new VipRight();
//        vipRight.setLevel(999);
//        vipRight.setExp(123456);
//        vipRight.getRewardedIds().addAll(Set.of(1, 2, 3));
//        PlayerEnt player = GameContext.getPlayerService().getPlayer(10000L);
//        player.setVipRight(vipRight);
//        GameContext.getPlayerService().savePlayer(player);
    }

    @Override
    public void onCenterServerConnected() {
        IdSession centerSession = GameContext.getBean(RpcClientRouter.class).getCenterSession();
        if (centerSession != null) {
            centerSession.sendPacket(new Rpc_G2C_FetchFightServerNodes());
        }
    }
}
