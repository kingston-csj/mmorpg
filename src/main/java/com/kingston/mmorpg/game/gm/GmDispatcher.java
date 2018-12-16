package com.kingston.mmorpg.game.gm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.message.CmdExecutor;
import com.kingston.mmorpg.game.logs.LoggerUtils;
import com.kingston.mmorpg.game.scene.actor.Player;

@Component
public class GmDispatcher {
	
	/** [methodName, CmdExecutor] */
	private static final Map<String, CmdExecutor> GM_HANDLERS = new HashMap<>();
	
	public void registerHandler(String key, CmdExecutor executor) {
		GM_HANDLERS.put(key, executor);
	}
	
	public void dispatch(Player player, String[] args) {
		String method = args[0];
		Object[] methodParams = new Object[args.length];
		methodParams[0] = player;
		for (int i = 1; i < methodParams.length; i++) {
			methodParams[i] = args[i];
		}
		
		for (Map.Entry<String, CmdExecutor> entry : GM_HANDLERS.entrySet()) {
			String targetMethod = entry.getKey();
			if (method.equalsIgnoreCase(targetMethod)) {
				CmdExecutor executor = entry.getValue();
				try {
					executor.getMethod().invoke(executor.getHandler(), methodParams);
				} catch (Exception e) {
					LoggerUtils.error("", e);
				}
				break;
			}
		}
	}

}
