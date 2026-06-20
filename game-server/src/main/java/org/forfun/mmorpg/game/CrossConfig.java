package org.forfun.mmorpg.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
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

	public int getCenterId() {
		return centerId;
	}

	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	public String getCenterIp() {
		return centerIp;
	}

	public void setCenterIp(String centerIp) {
		this.centerIp = centerIp;
	}

	public int getCenterPort() {
		return centerPort;
	}

	public void setCenterPort(int centerPort) {
		this.centerPort = centerPort;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
