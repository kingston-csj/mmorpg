package org.forfun.mmorpg.game.function.service;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.config.domain.ConfigFunction;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.function.event.PlayerFuncOpenEvent;
import org.forfun.mmorpg.game.function.model.FunctionOpenType;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 功能开启与关闭
 */
@Service
public class FunctionService {


    public void checkOpen(PlayerEnt player, int type) {
        Collection<ConfigFunction> targets = GameContext.getDataManager().queryByIndex(ConfigFunction.class,"openType", FunctionOpenType.Level.getType());
        targets.stream().filter(func -> !player.isOpenedFunction(func.getId()))
                .forEach(func -> {
                    if (player.getLevel() >= func.getOpenMainParam()) {
                        levelOpenFunc(player, func.getId());
                    }
                });
    }

    private void levelOpenFunc(PlayerEnt player, int funcId) {
        GameContext.getPlayerService().savePlayer(player);
        GameContext.getEventBus().post(new PlayerFuncOpenEvent(player));
    }

}
