package org.forfun.mmorpg.test.codec;

import org.forfun.mmorpg.protocol.Message;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

import java.util.Arrays;
import java.util.List;

@MessageMeta
public class ReqSelectPlayer implements Message {

    private long playerId;

    private String name;

    private long[] money;

    private List<Long> ids;

    private List<BaseVo> vos;

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

    public List<BaseVo> getVos() {
        return vos;
    }

    public void setVos(List<BaseVo> vos) {
        this.vos = vos;
    }


    public long[] getMoney() {
        return money;
    }

    public void setMoney(long[] money) {
        this.money = money;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ids == null) ? 0 : ids.hashCode());
        result = prime * result + Arrays.hashCode(money);
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
        ReqSelectPlayer other = (ReqSelectPlayer) obj;
        if (ids == null) {
            if (other.ids != null)
                return false;
        } else if (!ids.equals(other.ids))
            return false;
        if (!Arrays.equals(money, other.money))
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
        return "ReqSelectPlayer [playerId=" + playerId + ", name=" + name + ", money=" + Arrays.toString(money)
                + ", ids=" + ids + ", vos=" + vos + "]";
    }

}
