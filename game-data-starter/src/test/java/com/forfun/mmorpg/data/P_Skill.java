package com.forfun.mmorpg.data;

import org.forfun.mmorpg.data.annotation.Id;
import org.forfun.mmorpg.data.annotation.Index;
import org.forfun.mmorpg.data.annotation.PTable;

import java.util.List;
import java.util.Map;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getArr() {
        return arr;
    }

    public void setArr(Integer[] arr) {
        this.arr = arr;
    }

    public List<Consume> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<Consume> consumes) {
        this.consumes = consumes;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }
}
