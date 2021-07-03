package org.forfun.mmorpg.game.admin;

import org.forfun.mmorpg.game.base.SchedulerManager;
import org.forfun.mmorpg.game.logger.LoggerSystem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminFacade {

    @RequestMapping("/shutDown")
    public SimplyReply shutDown() {
        LoggerSystem.HTTP_COMMAND.getLogger().info("收到后台命令，准备停服");

        SchedulerManager.getInstance().schedule(() -> {
            // 发出关闭信号，交由ServerStartup的关闭钩子处理
            Runtime.getRuntime().exit(0);
        }, 5 * 1000);

        return SimplyReply.valueOfSucc("发出关服信号，5s后开始执行!");
    }


}
