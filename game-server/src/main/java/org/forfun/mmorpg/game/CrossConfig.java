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

	@Value("${center.id}")
	private int centerId;

	@Value("${center.ip}")
	private String centerIp;

	@Value("${center.port}")
	private int centerPort;

	@Value("${rpc.signKey}")
	private String sign;
}
