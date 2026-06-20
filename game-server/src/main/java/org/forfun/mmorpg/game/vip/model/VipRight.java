package org.forfun.mmorpg.game.vip.model;

import java.util.HashSet;
import java.util.Set;

public class VipRight {

    private int level;

    private int exp;

    private Set<Integer> rewardedIds = new HashSet<>();

    @Override
    public String toString() {
        return "VipRight [level=" + level + ", exp=" + exp + "]";
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Set<Integer> getRewardedIds() {
        return rewardedIds;
    }

    public void setRewardedIds(Set<Integer> rewardedIds) {
        this.rewardedIds = rewardedIds;
    }

}
