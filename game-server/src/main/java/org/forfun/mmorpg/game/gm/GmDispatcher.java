package org.forfun.mmorpg.game.gm;

import jforgame.socket.share.message.MessageExecutor;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class GmDispatcher {

	@Autowired()
	@Qualifier("gameConversion")
	private ConversionService conversionService;

	/** [methodName, CmdExecutor] */
	private static final Map<String, MessageExecutor> GM_HANDLERS = new HashMap<>();

	public void registerHandler(String key, MessageExecutor executor) {
		GM_HANDLERS.put(key, executor);
	}

	public void dispatch(PlayerEnt player, String[] args) {
		String method = args[0];

		for (Map.Entry<String, MessageExecutor> entry : GM_HANDLERS.entrySet()) {
			String targetMethod = entry.getKey();
			if (method.equalsIgnoreCase(targetMethod)) {
				MessageExecutor executor = entry.getValue();
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
