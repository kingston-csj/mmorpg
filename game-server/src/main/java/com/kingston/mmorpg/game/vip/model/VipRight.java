package com.kingston.mmorpg.game.vip.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class VipRight {

    private int level;

    private int exp;

    private Set<Integer> rewardedIds = new HashSet<>();

    @Override
    public String toString() {
        return "VipRight [level=" + level + ", exp=" + exp + "]";
    }

}
