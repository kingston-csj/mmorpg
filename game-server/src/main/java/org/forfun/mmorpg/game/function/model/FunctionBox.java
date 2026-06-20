package org.forfun.mmorpg.game.function.model;

import java.util.HashSet;
import java.util.Set;

public class FunctionBox {

    private Set<Integer> opened = new HashSet<>();

    public Set<Integer> getOpened() {
        return opened;
    }

    public void setOpened(Set<Integer> opened) {
        this.opened = opened;
    }
}
