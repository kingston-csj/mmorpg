package org.forfun.mmorpg.csv;

import org.forfun.mmorpg.csv.reader.CsvReader;
import org.forfun.mmorpg.csv.reader.DataReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

@SpringBootApplication()
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class);
        DataReader reader = new CsvReader();
        Resource resource = new ClassPathResource("p_skill.csv");
        List<P_Skill> skills = reader.read(resource.getInputStream(), P_Skill.class);

        System.out.println(skills);
        // [P_Skill(skillId=1, level=1, name=飞龙探云手, arr=[1, 2, 3], consumes=[org.forfun.mmorpg.csv.Consume@29a4f594], map={item=11, num=1}, type=ActiveSkill), P_Skill(skillId=2, level=1, name=“三味真火”, arr=[1, 2, 3], consumes=[], map={item=11, num=1}, type=ActiveSkill), P_Skill(skillId=3, level=1, name=酒神, arr=[1, 2, 3], consumes=[], map={item=11, num=1}, type=PassiveSkill)]
    }
}
