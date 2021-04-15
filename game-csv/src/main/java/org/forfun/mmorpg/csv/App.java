package org.forfun.mmorpg.csv;

import org.forfun.mmorpg.csv.reader.CsvReader;
import org.forfun.mmorpg.csv.reader.DataReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@SpringBootApplication()
public class App   {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class);
        DataReader reader = new CsvReader();

        URL resource = App.class.getResource("/p_skill.csv");
        InputStream inputStream = new FileInputStream(resource.getPath());
        List<P_Skill> skills = reader.read(inputStream, P_Skill.class);

        System.out.println(skills);
    }
}
