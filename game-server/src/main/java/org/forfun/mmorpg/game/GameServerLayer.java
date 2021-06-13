package org.forfun.mmorpg.game;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.vip.model.VipRight;

import java.util.Set;

public class GameServerLayer implements ServerLayer {

    @Override
    public void init() {
        VipRight vipRight = new VipRight();
        vipRight.setLevel(999);
        vipRight.setExp(123456);
        vipRight.getRewardedIds().addAll(Set.of(1, 2, 3));
        PlayerEnt player = GameContext.getPlayerService().getPlayer(10000L);
        player.setVipRight(vipRight);
        GameContext.getPlayerService().savePlayer(player);
    }
}
