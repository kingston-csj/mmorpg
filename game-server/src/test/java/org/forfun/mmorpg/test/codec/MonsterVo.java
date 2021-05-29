package org.forfun.mmorpg.test.codec;


import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@MessageMeta(cmd = 3)
public class MonsterVo extends BaseVo {
	
	private long hp;
	
	public MonsterVo() {
		super();
	}

	public MonsterVo(long hp) {
		super();
		this.hp = hp;
	}

	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	@Override
	public String toString() {
		return "MonsterVo [hp=" + hp + ", id=" + id + "]";
	}
	
	

}
