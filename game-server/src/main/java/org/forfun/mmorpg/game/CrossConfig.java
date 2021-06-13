package org.forfun.mmorpg.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@DependsOn("serverConfigFactory")
public class CrossConfig {

	@Value("${rpc.center.id}")
	private int centerId;

	@Value("${rpc.center.ip}")
	private String centerIp;

	@Value("${rpc.center.port}")
	private int centerPort;

	@Value("${rpc.signKey}")
	private String sign;
}
