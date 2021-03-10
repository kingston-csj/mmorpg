package org.forfun.mmorpg.game.function.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class FunctionBox {

    private Set<Integer> opened = new HashSet<>();
}
