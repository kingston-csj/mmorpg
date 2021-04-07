package org.forfun.mmorpg.csv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class App   {
//    @Override
//    public void run(String... args) throws Exception {
//        final App server = new App();
//        System.out.println("hello");
//    }

    public static void main(String[] args) throws Exception {
//        SpringApplication app = new SpringApplication(App.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(new String[]{});
        SpringApplication.run(App.class);
        System.out.println("hello");
        CsvReader.main(null);
    }
}
