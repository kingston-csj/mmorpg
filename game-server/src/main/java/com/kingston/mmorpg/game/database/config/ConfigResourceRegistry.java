package com.kingston.mmorpg.game.database.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 静态配置数据仓库
 * 
 * @author kingston
 *
 */
@Component
public class ConfigResourceRegistry implements ApplicationContextAware {

	@PostConstruct
	private void init() {
		loadAllConfigs();
	}

	public void loadAllConfigs() {
//		Field[] fields = ConfigResourceRegistry.class.getDeclaredFields();
//		for (Field f:fields) {
//			try {
//			if (Reloadable.class.isAssignableFrom(f.getType())) {
//				Reloadable container = (Reloadable) f.getType().newInstance();
//				container.reload();
//				f.set(this, container);
//			}
//			}catch (Exception e) {
//				LoggerUtils.error("策划配置数据有误，请检查", e);
//				System.exit(0);
//			}
//		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, Reloadable> configs = applicationContext.getBeansOfType(Reloadable.class);
		for (Map.Entry<String, Reloadable> entry : configs.entrySet()) {
			Reloadable config = entry.getValue();
			config.reload();
		}
	}

}
