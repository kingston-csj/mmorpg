package org.forfun.mmorpg.csv;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class P_Skill {

    private int skillId;

    private int level;

    private String name;

    private Integer[] arr;

    private List<Consume> consumes;

    private Map<String, String> map;

    private SkillType type;
}
