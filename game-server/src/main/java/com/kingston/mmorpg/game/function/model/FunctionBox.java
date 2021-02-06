package com.kingston.mmorpg.game.function.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public class FunctionBox {

    private Set<Integer> opened = new HashSet<>();
}
