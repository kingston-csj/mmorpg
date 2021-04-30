package com.forfun.mmorpg.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.forfun.mmorpg.data.annotation.Id;
import org.forfun.mmorpg.data.annotation.Index;
import org.forfun.mmorpg.data.annotation.PTable;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@PTable
public class P_Skill {

    @Id
    private int id;

    @Index
    private int skillId;

    private int level;

    private String name;

    private Integer[] arr;

    private List<Consume> consumes;

    private Map<String, String> map;

    private SkillType type;
}
