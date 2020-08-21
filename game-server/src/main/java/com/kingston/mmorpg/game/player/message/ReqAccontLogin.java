package com.kingston.mmorpg.game.player.message;

//import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.service.PlayerService;
import lombok.Getter;
import lombok.Setter;

@MessageMeta(cmd = PlayerService.CMD_REQ_ACCOUNT_LOGIN)
public class ReqAccontLogin extends Message {

//	@Protobuf
	private long accountId;

//	@Protobuf
	private String password;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
