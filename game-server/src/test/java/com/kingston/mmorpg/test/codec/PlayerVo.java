package com.kingston.mmorpg.test.codec;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;

@MessageMeta(cmd = 2)
public class PlayerVo extends BaseVo {
	
	private int age;
	
	private String name;
	
	public PlayerVo() {
		
	}
	
	public PlayerVo(int age, String name) {
		super();
		this.age = age;
		this.name = name;
		this.id = System.currentTimeMillis();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PlayerVo other = (PlayerVo) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerVo [age=" + age + ", name=" + name + ", id=" + id + "]";
	}
}
