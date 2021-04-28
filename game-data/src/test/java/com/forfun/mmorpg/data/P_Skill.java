package com.forfun.mmorpg.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class P_Skill {

    private int skillId;

    private int level;

    private String name;

    private Integer[] arr;

    private List<Consume> consumes;

    private Map<String, String> map;

    private SkillType type;
}
