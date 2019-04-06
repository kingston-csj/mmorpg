package com.kingston.mmorpg.game.gm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.message.CmdExecutor;
import com.kingston.mmorpg.game.logs.LoggerUtils;
import com.kingston.mmorpg.game.scene.actor.Player;

@Component
public class GmDispatcher {

	@Autowired()
	@Qualifier("gameConversion")
	private ConversionService conversionService;

	/** [methodName, CmdExecutor] */
	private static final Map<String, CmdExecutor> GM_HANDLERS = new HashMap<>();

	public void registerHandler(String key, CmdExecutor executor) {
		GM_HANDLERS.put(key, executor);
	}

	public void dispatch(Player player, String[] args) {
		String method = args[0];

		for (Map.Entry<String, CmdExecutor> entry : GM_HANDLERS.entrySet()) {
			String targetMethod = entry.getKey();
			if (method.equalsIgnoreCase(targetMethod)) {
				CmdExecutor executor = entry.getValue();
				try {
					// 动态参数要求只能有两个参数，第二个需要是 String[] 类型
					if (isDynamicParams(executor.getMethod())) {
						String[] dynParams = new String[args.length - 1];
						for (int i = 0; i < dynParams.length; i++) {
							dynParams[i] = args[i + 1];
						}
						executor.getMethod().invoke(executor.getHandler(), player, dynParams);
					} else {
						Object[] methodParams = new Object[args.length];
						methodParams[0] = player;
						for (int i = 1; i < methodParams.length; i++) {
							methodParams[i] = conversionService.convert(args[i], executor.getParams()[i]);
						}
						executor.getMethod().invoke(executor.getHandler(), methodParams);
					}

				} catch (Exception e) {
					LoggerUtils.error("", e);
				}
				break;
			}
		}
	}

	private boolean isDynamicParams(Method method) {
		int paramSum = method.getParameterCount();
		if (paramSum <= 1) {
			return false;
		}
		return method.getParameterTypes()[paramSum - 1].isArray();
	}

}
