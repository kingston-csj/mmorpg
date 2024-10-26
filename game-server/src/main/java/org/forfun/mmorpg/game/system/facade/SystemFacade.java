package org.forfun.mmorpg.game.system.facade;

import jforgame.socket.share.annotation.MessageRoute;
import org.forfun.mmorpg.game.Modules;
import org.springframework.stereotype.Controller;

@Controller
@MessageRoute(module = Modules.BASE)
public class SystemFacade {
}
