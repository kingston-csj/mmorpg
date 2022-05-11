package org.forfun.mmorpg.game.cross.router;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

public class RandomBalanceStrategy implements BalanceStrategy {

    private Random random = new Random();

    @Override
    public int next(List<Integer> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return 0;
        }
        return nodes.get(random.nextInt() % nodes.size());
    }
}
