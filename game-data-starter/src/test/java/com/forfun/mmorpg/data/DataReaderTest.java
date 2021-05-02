package com.forfun.mmorpg.data;

import org.forfun.mmorpg.data.DataManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class)
public class DataReaderTest {

    @Autowired
    private DataManager dataManager;

    @Test
    public void doRead() throws Exception {
        dataManager.registerContainer(P_Skill.class);
        List<P_Skill> skills = dataManager.queryAll(P_Skill.class);

        // 把所有的测试代码放到 test目录下，在反序列化Consume对象时会报错。放在main目录则没有这个问题。我也不知道WHY
        System.out.println(skills);
        Assert.assertTrue(skills.size() == 3);
        Assert.assertTrue(dataManager.queryById(P_Skill.class, 1) != null);
        Assert.assertTrue(dataManager.queryByIndex(P_Skill.class, "skillId", 1).size() == 2);
        // [P_Skill(skillId=1, level=1, name=飞龙探云手, arr=[1, 2, 3], consumes=[org.forfun.mmorpg.csv.Consume@29a4f594], map={item=11, num=1}, type=ActiveSkill), P_Skill(skillId=2, level=1, name=“三味真火”, arr=[1, 2, 3], consumes=[], map={item=11, num=1}, type=ActiveSkill), P_Skill(skillId=3, level=1, name=酒神, arr=[1, 2, 3], consumes=[], map={item=11, num=1}, type=PassiveSkill)]
    }

}
