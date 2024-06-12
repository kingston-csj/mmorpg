package org.forfun.mmorpg.game;

import jakarta.annotation.PostConstruct;
import org.forfun.mmorpg.game.base.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

@Configuration
public class ServerConfigFactory {

    @Autowired
    private StandardEnvironment environment;

    @PostConstruct
    public void init() throws IOException {
        switch (GameContext.serverType) {
            case GATE -> environment.getPropertySources().addLast(new ResourcePropertySource("file:config/gate.properties"));
            case GAME -> environment.getPropertySources().addLast(new ResourcePropertySource("file:config/server.properties"));
            case CENTRE -> environment.getPropertySources().addLast(new ResourcePropertySource("file:config/center.properties"));
        }
        environment.getPropertySources().addLast(new ResourcePropertySource("file:config/common.properties"));
    }

}
