package com.kingston.mmorpg.test.codec;

import java.util.List;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;

@MessageMeta
public class ReqSelectePlayer extends Message {
	
	private long playerId;
	
	private String name;
	
	private List<Long> ids;
	
	private List<PlayerVo> vos;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	
	public List<PlayerVo> getVos() {
		return vos;
	}

	public void setVos(List<PlayerVo> vos) {
		this.vos = vos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ids == null) ? 0 : ids.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (playerId ^ (playerId >>> 32));
		result = prime * result + ((vos == null) ? 0 : vos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReqSelectePlayer other = (ReqSelectePlayer) obj;
		if (ids == null) {
			if (other.ids != null)
				return false;
		} else if (!ids.equals(other.ids))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playerId != other.playerId)
			return false;
		if (vos == null) {
			if (other.vos != null)
				return false;
		} else if (!vos.equals(other.vos))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ReqSelectePlayerMsg [playerId=" + playerId + ", name=" + name + ", ids=" + ids + ", vos=" + vos + "]";
	}

}
