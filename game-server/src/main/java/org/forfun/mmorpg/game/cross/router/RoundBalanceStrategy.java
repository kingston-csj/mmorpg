package org.forfun.mmorpg.game.cross.router;


import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundBalanceStrategy implements BalanceStrategy {

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public int next(List<Integer> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return 0;
        }
        return nodes.get(counter.getAndIncrement() % nodes.size());
    }

}
